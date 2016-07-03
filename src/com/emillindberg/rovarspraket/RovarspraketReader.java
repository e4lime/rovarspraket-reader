package com.emillindberg.rovarspraket;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
/**
 * Use to decorate a reader with Astrid Lindgrens "Röverspråket" as described in the Kalle Blomkvist books.
 * The class checks for consonants when it reads from file. If a consonant is found the class will read from its memory
 * instead of the input. Some effort is made to handle different capitalization cases if RovarspraktedReader decorates
 * a PushbackReader.
 *
 * ---Example outcomes---
 * Decorating a InputStreamReader:
 * Hello!  ->  Hohelollolo!
 * HELLO!  ->  HohELolLolO!
 * HELlo   ->  HohELollolo!
 *
 * Decorating a PushbackReader:
 * Hello! -> Hohelollolo!
 * HELLO! -> HOHELOLLOLO!
 * HELlo  -> HOHELOLlolo!
 *
 * --------
 * Todo: Capitalization on decorating BufferedReader (using mark and reset)
 * Created by Emil Lindberg on 2015-12-08.
 */
public class RovarspraketReader extends FilterReader {


    private static final Character[] ALL_VOCALS_LOWERCASE_VALUES = {'a', 'o', 'u', 'å', 'e', 'i', 'y', 'ä', 'ö'};
    private static final Set<Character> vocalsLowerCase = new HashSet<Character>(Arrays.asList(ALL_VOCALS_LOWERCASE_VALUES));
    private static final char VOCAL_TO_ADD = 'o';
    private static final int END_OF_STREAM = -1;

    private boolean processConsonant = false;
    private int sectionIndex = 0;
    private Character sectionConsonant;
    private Character sectionVocal = VOCAL_TO_ADD;
    private boolean sectionUpperCase = false;
    private Character lastPrintedChar = ' ';
    private static final int SECTION_LENGTH = 1;

    public RovarspraketReader(Reader in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        if (processConsonant) {
            return processSection();
        }

        int c = super.read();
        if (c != END_OF_STREAM) {
            char asChar = (char)c;
            if (RovarspraketReader.isConsonant(asChar)) {
                initProcessConsonant(asChar);
            }
        }
        lastPrintedChar = (char)c;
        return c;
    }



    private int processSection(){
        int c = changeCase(sectionVocal, sectionUpperCase);
        if (sectionIndex++ == SECTION_LENGTH) {
            c = changeCase(sectionConsonant, sectionUpperCase);
            processConsonant = false;
        }
        return c;
    }

    private static Character peekNextChar(PushbackReader pbr) throws IOException{
        Character c = (char)pbr.read();
        pbr.unread(c);
        return c;

    }


    // A section should be created when a consonant is detected.
    private void initProcessConsonant(Character consonant) throws IOException {
        processConsonant = true;
        sectionConsonant = consonant;
        sectionVocal = VOCAL_TO_ADD;
        sectionIndex = 0;
        if (in instanceof PushbackReader) {
            updateCapitalization();
        }

    }

    private void updateCapitalization() throws IOException {
        if (!(in instanceof PushbackReader)) {
            throw new IllegalStateException("Decorated reader isn't a PushbackReader");
        }
        Character nextChar =  RovarspraketReader.peekNextChar((PushbackReader)in);
        if (Character.isUpperCase(sectionConsonant) && !Character.isLetter(nextChar) ) {
            sectionUpperCase = true;
        }
        else if (Character.isUpperCase(sectionConsonant) && RovarspraketReader.isCapitalizedLetter(nextChar)) {
            sectionUpperCase = true;
        }
        else if (Character.isUpperCase(sectionConsonant) && isCapitalizedLetter(lastPrintedChar)){
            sectionUpperCase = true;
        }
        else {
            sectionUpperCase = false;
        }
    }

    private static boolean isCapitalizedLetter(Character c) {
        return (Character.isLetter(c) && Character.isUpperCase(c));
    }


    private static Character changeCase(Character target, boolean toUpperCase) {
        if (toUpperCase) {
            return Character.toUpperCase(target);
        }
        else {
            return Character.toLowerCase(target);
        }
    }
    private static boolean isConsonant(Character toCheck){
        return Character.isLetter(toCheck) && !vocalsLowerCase.contains(Character.toLowerCase(toCheck));
    }
}
