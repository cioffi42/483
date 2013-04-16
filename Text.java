import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Iterator;


public class Text {
    
    private static final String[][] unicodeLetters = {
        {"\\alpha", "\u03b1"},
        {"\\omega", "\u03c9"},
        {"\\theta", "\u03b8"},
        {"\\tau", "\u03c4"},
        {"\\mu", "\u03bc"},
        {"\\Sigma", "\u03c3"},
        {"\\Delta", "\u0394"},
        {"\\phi", "\u03c6"},
        {"\\int", "\u222b"},
        {"<=", "\u2264"},
        {">=", "\u2265"}
    };
    
    public static String applyUnicodeCharacters(String text){
        StringBuilder sb = new StringBuilder(text);
        for (String[] unicodePair : unicodeLetters){
            int index = sb.indexOf(unicodePair[0]);
            while (index != -1){
                sb.replace(index, index + unicodePair[0].length(), unicodePair[1]);
                index = sb.indexOf(unicodePair[0]);
            }
        }
        return sb.toString();
    }
    
    public static AttributedString applyStringStyles(String text, int fontSize){
        StringBuilder sb = new StringBuilder();
        ArrayList<Integer[]> subscripts = new ArrayList<Integer[]>();
        ArrayList<Integer[]> superscripts = new ArrayList<Integer[]>();
        int numDeletedChars = 0;
        for (int i=0; i<text.length(); i++){
            char ch = text.charAt(i);
            if (ch == '^'){
                // We want to add a superscript (so x^2 becomes x squared)
                numDeletedChars++;
                int j = i+1;
                while (j < text.length() && Character.isLetterOrDigit(text.charAt(j))){
                    j++;
                }
                Integer[] superscriptIndices = {i+1 - numDeletedChars, j - numDeletedChars};
                superscripts.add(superscriptIndices);
            } else if (ch == '_'){
                // We want to add a subscript (so F_G becomes F subscript G)
                numDeletedChars++;
                int j = i+1;
                while (j < text.length() && Character.isLetterOrDigit(text.charAt(j))){
                    j++;
                }
                Integer[] subscriptIndices = {(i+1) - numDeletedChars, j - numDeletedChars};
                subscripts.add(subscriptIndices);
            } else {
                sb.append(ch);
            }
        }
        
        // Now create the AttributedString with superscripts and subscripts
        AttributedString result = new AttributedString(sb.toString());
        Iterator<Integer[]> it;
        it = superscripts.iterator();
        while (it.hasNext()){
            Integer[] indices = it.next();
            result.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, indices[0], indices[1]);
        }
        it = subscripts.iterator();
        while (it.hasNext()){
            Integer[] indices = it.next();
            result.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, indices[0], indices[1]);
        }
        
        // Finally, give the AttributedString the right font
        result.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        result.addAttribute(TextAttribute.SIZE, new Float(fontSize));
        
        return result;
    }
}
