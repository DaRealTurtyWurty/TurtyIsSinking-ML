package dev.turtywurty.turtyissinking.util.uwu;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Uwuifier {
    public static final Uwuifier DEFAULT = new Uwuifier();

    private static final Pattern EXCLAMATION_PATTERN = Pattern.compile("[?!]+$");
    private static final Pattern URI_ILLEGAL_CHARACTER_PATTERN = Pattern.compile("[^a-z0-9:/?#\\[\\]@!$&'()*+,;=.\\-_~%]", Pattern.CASE_INSENSITIVE);
    private static final Pattern URI_BAD_ESCAPE_PATTERN = Pattern.compile("%[^0-9a-f]|%[0-9a-f](?:[^0-9a-f]|$)", Pattern.CASE_INSENSITIVE);
    private static final Pattern URI_PATTERN = Pattern.compile("(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*)(?:\\?([^#]*))?(?:#(.*))?");
    private static final Pattern SCHEME_PATTERN = Pattern.compile("^[a-z][a-z0-9+\\-.]*$");
    private static final Pattern LETTER_PATTERN = Pattern.compile("^[a-zA-Z]$");
    private static final Pattern SENTENCE_END_PATTERN = Pattern.compile("[.!?\\-]");

    public static final List<String> FACES = Arrays.asList(
            "(・`ω´・)",
            ";;w;;",
            "OwO",
            "UwU",
            ">w<",
            "^w^",
            "ÚwÚ",
            "^-^",
            ":3",
            "x3"
    );

    public static final List<String> EXCLAMATIONS = Arrays.asList("!?", "?!!", "?!?1", "!!11", "?!?!");

    public static final List<String> ACTIONS = Arrays.asList(
            "*blushes*",
            "*whispers to self*",
            "*cries*",
            "*screams*",
            "*sweats*",
            "*twerks*",
            "*runs away*",
            "*screeches*",
            "*walks away*",
            "*sees bulge*",
            "*looks at you*",
            "*notices buldge*",
            "*starts twerking*",
            "*huggles tightly*",
            "*boops your nose*"
    );

    private final SpacesModifier spacesModifier;
    private final double wordsModifier;
    private final double exclamationsModifier;

    public Uwuifier() {
        this(SpacesModifier.DEFAULT, 1, 1);
    }

    public Uwuifier(SpacesModifier spacesModifier, double wordsModifier, double exclamationsModifier) {
        this.spacesModifier = spacesModifier == null ? SpacesModifier.DEFAULT : spacesModifier;
        validateModifier(this.spacesModifier.faces() + this.spacesModifier.actions() + this.spacesModifier.stutters(), "spaces");
        validateModifier(wordsModifier, "words");
        validateModifier(exclamationsModifier, "exclamations");
        this.wordsModifier = wordsModifier;
        this.exclamationsModifier = exclamationsModifier;
    }

    public String uwuify(String input) {
        String uwuified = input;
        uwuified = uwuifyWords(uwuified);
        uwuified = uwuifyExclamations(uwuified);
        uwuified = uwuifySpaces(uwuified);
        return uwuified;
    }

    public String uwuifyWords(String sentence) {
        String[] words = sentence.split(" ", -1);
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (isUri(word))
                continue;

            var seed = new UwuSeed(word);
            word = replaceIfAllowed(seed, word, "(?:r|l)", "w"); // hello -> hewwo
            word = replaceIfAllowed(seed, word, "(?:R|L)", "w"); // ARISE -> AWISE
            word = replaceIfAllowed(seed, word, "n([aeiou])", "ny$1"); // nice -> nyce
            word = replaceIfAllowed(seed, word, "N([aeiou])", "Ny$1"); // Nice -> Nyce
            word = replaceIfAllowed(seed, word, "N([AEIOU])", "NY$1"); // NICE -> NYCE
            word = replaceIfAllowed(seed, word, "ove", "uv"); // love -> luv
            words[i] = word;
        }

        return String.join(" ", words);
    }

    public String uwuifyExclamations(String sentence) {
        String[] words = sentence.split(" ", -1);
        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            var seed = new UwuSeed(word);
            if (!EXCLAMATION_PATTERN.matcher(word).find() || seed.random() > this.exclamationsModifier)
                continue;

            word = EXCLAMATION_PATTERN.matcher(word).replaceAll("");
            word += EXCLAMATIONS.get(seed.randomInt(0, EXCLAMATIONS.size() - 1));
            words[i] = word;
        }

        return String.join(" ", words);
    }

    public String uwuifySpaces(String sentence) {
        String[] words = sentence.split(" ", -1);

        double faceThreshold = this.spacesModifier.faces();
        double actionThreshold = faceThreshold + this.spacesModifier.actions();
        double stutterThreshold = actionThreshold + this.spacesModifier.stutters();

        for (int index = 0; index < words.length; index++) {
            String word = words[index];
            if (word.isEmpty())
                continue;

            var seed = new UwuSeed(word);
            double randomValue = seed.random();
            char firstCharacter = word.charAt(0);

            if (randomValue < faceThreshold && !FACES.isEmpty()) {
                word += " " + FACES.get(seed.randomInt(0, FACES.size() - 1));
                word = checkCapital(words, index, word, firstCharacter);
            } else if (randomValue < actionThreshold && !ACTIONS.isEmpty()) {
                word += " " + ACTIONS.get(seed.randomInt(0, ACTIONS.size() - 1));
                word = checkCapital(words, index, word, firstCharacter);
            } else if (randomValue < stutterThreshold && !isUri(word)) {
                int stutter = seed.randomInt(0, 2);
                word = (firstCharacter + "-").repeat(stutter) + word;
            }

            words[index] = word;
        }

        return String.join(" ", words);
    }

    private static String checkCapital(String[] words, int index, String word, char firstCharacter) {
        if (firstCharacter != Character.toUpperCase(firstCharacter))
            return word;

        if (getCapitalPercentage(word) > 0.5)
            return word;

        if (index == 0)
            return Character.toLowerCase(firstCharacter) + word.substring(1);

        String previousWord = words[index - 1];
        char previousWordLastChar = previousWord.charAt(previousWord.length() - 1);
        if (!SENTENCE_END_PATTERN.matcher(String.valueOf(previousWordLastChar)).find())
            return word;

        return Character.toLowerCase(firstCharacter) + word.substring(1);
    }

    private static double getCapitalPercentage(String input) {
        int totalLetters = 0;
        int upperLetters = 0;

        for (int i = 0; i < input.length(); i++) {
            String currentLetter = String.valueOf(input.charAt(i));
            if (LETTER_PATTERN.matcher(currentLetter).matches()) {
                totalLetters++;
                if (currentLetter.equals(currentLetter.toUpperCase())) {
                    upperLetters++;
                }
            }
        }

        return totalLetters == 0 ? 0 : (double) upperLetters / totalLetters;
    }

    private String replaceIfAllowed(UwuSeed seed, String word, String regex, String replacement) {
        if (seed.random() > this.wordsModifier)
            return word;

        return word.replaceAll(regex, replacement);
    }

    private static boolean isUri(String value) {
        if (value == null || value.isBlank())
            return false;

        if (URI_ILLEGAL_CHARACTER_PATTERN.matcher(value).find())
            return false;

        if (URI_BAD_ESCAPE_PATTERN.matcher(value).find())
            return false;

        Matcher split = URI_PATTERN.matcher(value);
        if (!split.matches())
            return false;

        String scheme = split.group(1);
        String authority = split.group(2);
        String path = split.group(3);
        if (scheme == null || scheme.isEmpty() || path == null)
            return false;

        if (authority != null && !authority.isEmpty()) {
            if (!(path.isEmpty() || path.startsWith("/")))
                return false;
        } else if (path.startsWith("//"))
            return false;

        return SCHEME_PATTERN.matcher(scheme.toLowerCase(Locale.ROOT)).matches();
    }

    private static void validateModifier(double modifier, String name) {
        if (modifier < 0 || modifier > 1)
            throw new IllegalArgumentException(name + " must be between 0 and 1");
    }

    public record SpacesModifier(double faces, double actions, double stutters) {
        private static final SpacesModifier DEFAULT = new SpacesModifier(0.05, 0.075, 0.1);

        public SpacesModifier {
            if (faces < 0 || actions < 0 || stutters < 0)
                throw new IllegalArgumentException("All values must be non-negative");
        }
    }
}
