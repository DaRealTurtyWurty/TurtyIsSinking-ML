package dev.turtywurty.turtyissinking.effect;

import dev.turtywurty.turtyissinking.Constants;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.ThreadLocalRandom;

public class ADHDMobEffect extends MobEffect {
    private final RandomSource random = new SingleThreadedRandomSource(ThreadLocalRandom.current().nextLong());

    public ADHDMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public ADHDMobEffect(MobEffectCategory category, int color, ParticleOptions particleOptions) {
        super(category, color, particleOptions);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification) {
        return this.random.nextInt(Math.max(1, (10 * 20) / (amplification + 1))) == 0;
    }

    @Override
    public boolean applyEffectTick(@NonNull ServerLevel serverLevel, @NonNull LivingEntity mob, int amplification) {
        ADHDAction action = ADHDAction.values()[this.random.nextInt(ADHDAction.values().length)];

        boolean performed = false;
        while (!performed) {
            switch (action) {
                case MOVE -> performed = handleMove(mob);
                case LOOK_AROUND -> performed = handleLookAround(mob);
                case JUMP -> performed = handleJump(mob);
                case CROUCH -> performed = handleCrouch(mob);
                case INTERACT -> performed = handleInteraction(serverLevel, mob);
                case DROP_ITEM -> performed = handleDropItem(mob);
                case USE_ITEM -> performed = handleUseItem(mob);
                case ATTACK -> performed = handleAttack(serverLevel, mob);
                case SWAP_HANDS -> performed = handleSwapHands(mob);
            }

            if (!performed) {
                action = ADHDAction.values()[this.random.nextInt(ADHDAction.values().length)];
            }
        }

        Constants.LOG.info("ADHDMobEffect: Applied action {} to entity {}", action, mob);
        return true;
    }

    private boolean handleMove(LivingEntity mob) {
        mob.setDeltaMovement(mob.getDeltaMovement().add(
                (this.random.nextFloat() - 0.5f) * 0.2f,
                0,
                (this.random.nextFloat() - 0.5f) * 0.2f
        ));
        syncMovement(mob);
        return true;
    }

    private boolean handleLookAround(LivingEntity mob) {
        Vec3 randLook = mob.getEyePosition().add(
                (this.random.nextFloat() - 0.5f) * 4,
                (this.random.nextFloat() - 0.5f) * 2,
                (this.random.nextFloat() - 0.5f) * 4
        );

        mob.lookAt(EntityAnchorArgument.Anchor.EYES, randLook);
        syncLook(mob);
        return true;
    }

    private static void syncLook(LivingEntity mob) {
        if (mob instanceof ServerPlayer player) {
            player.connection.teleport(
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    player.getYRot(),
                    player.getXRot()
            );
        }
    }

    private boolean handleJump(LivingEntity mob) {
        if (mob.onGround()) {
            mob.jumpFromGround();
            syncMovement(mob);
            return true;
        }

        return false;
    }

    private static void syncMovement(LivingEntity mob) {
        if (mob instanceof ServerPlayer player) {
            player.connection.send(new ClientboundSetEntityMotionPacket(player));
        }
    }

    private boolean handleCrouch(LivingEntity mob) {
        mob.setShiftKeyDown(!mob.isCrouching());
        return true;
    }

    private boolean handleInteraction(ServerLevel serverLevel, LivingEntity mob) {
        if (mob instanceof ServerPlayer player) {
            // raycast for entities or blocks in front of the player and interact with them
            // entities first
            Vec3 start = player.getEyePosition();
            Vec3 end = start.add(player.getLookAngle().scale(5.0D));

            AABB searchBox = player.getBoundingBox()
                    .expandTowards(player.getLookAngle().scale(5.0D))
                    .inflate(1.0D);

            EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                    player,
                    start,
                    end,
                    searchBox,
                    entity -> entity.isPickable() && entity != player,
                    5.0D * 5.0D
            );

            if (entityHit != null) {
                Entity target = entityHit.getEntity();

                InteractionResult result = target.interact(player, InteractionHand.MAIN_HAND, entityHit.getLocation());
                if (!result.consumesAction()) {
                    target.interact(player, InteractionHand.OFF_HAND, entityHit.getLocation());
                }

                return true;
            }

            // then blocks
            BlockHitResult blockHitResult = serverLevel.clip(new ClipContext(
                    start, end,
                    ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
                    mob));
            if (blockHitResult.getType() != BlockHitResult.Type.MISS && player.mayInteract(serverLevel, blockHitResult.getBlockPos())) {
                InteractionResult result = player.gameMode.useItemOn(
                        player,
                        serverLevel,
                        player.getItemInHand(InteractionHand.MAIN_HAND),
                        InteractionHand.MAIN_HAND,
                        blockHitResult
                );

                if (!result.consumesAction()) {
                    player.gameMode.useItemOn(
                            player,
                            serverLevel,
                            player.getItemInHand(InteractionHand.OFF_HAND),
                            InteractionHand.OFF_HAND,
                            blockHitResult
                    );
                }

                return true;
            }
        }

        return false;
    }

    private boolean handleDropItem(LivingEntity mob) {
        if (mob instanceof ServerPlayer player) {
            player.drop(player.getItemInHand(InteractionHand.MAIN_HAND), true);
            return true;
        }

        return false;
    }

    private boolean handleUseItem(LivingEntity mob) {
        if (mob instanceof ServerPlayer player) {
            player.startUsingItem(InteractionHand.MAIN_HAND);
            return true;
        }

        return false;
    }

    private boolean handleAttack(ServerLevel serverLevel, LivingEntity mob) {
        if (mob instanceof ServerPlayer player) {
            Vec3 start = player.getEyePosition();
            Vec3 end = start.add(player.getLookAngle().scale(5.0D));

            AABB searchBox = player.getBoundingBox()
                    .expandTowards(player.getLookAngle().scale(5.0D))
                    .inflate(1.0D);

            EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                    player,
                    start,
                    end,
                    searchBox,
                    entity -> entity.isAttackable() && entity != player,
                    5.0D * 5.0D
            );

            if (entityHit != null) {
                player.attack(entityHit.getEntity());
                player.swing(InteractionHand.MAIN_HAND, true);
                return true;
            }

            BlockHitResult blockHitResult = serverLevel.clip(new ClipContext(
                    start, end,
                    ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE,
                    player));

            if (blockHitResult.getType() != BlockHitResult.Type.MISS) {
                Direction direction = blockHitResult.getDirection();
                player.gameMode.handleBlockBreakAction(
                        blockHitResult.getBlockPos(),
                        ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK,
                        direction,
                        serverLevel.getMaxY(),
                        0
                );
                player.swing(InteractionHand.MAIN_HAND, true);
                return true;
            }
        }

        mob.swing(InteractionHand.MAIN_HAND, true);
        return true;
    }

    private boolean handleSwapHands(LivingEntity mob) {
        if (mob instanceof ServerPlayer player) {
            ItemStack mainHand = player.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack offHand = player.getItemInHand(InteractionHand.OFF_HAND);

            player.setItemInHand(InteractionHand.MAIN_HAND, offHand);
            player.setItemInHand(InteractionHand.OFF_HAND, mainHand);
            player.stopUsingItem();

            return true;
        }

        return false;
    }

    public enum ADHDAction {
        MOVE,
        LOOK_AROUND,
        JUMP,
        CROUCH,
        INTERACT,
        DROP_ITEM,
        USE_ITEM,
        ATTACK,
        SWAP_HANDS
    }
}
