//https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// we could count to the last index but this breaks if there is normal text after
// we have to count the number of open parenthesis and match it with the number of closed parenthesis
// but what if there is a open and close parenthesis after 
public class MarkdownParse {
    
    public static Map<String, List<String>> getLinks(File dirOrFile) throws IOException {
        Map<String, List<String>> result = new HashMap<>();
        if(dirOrFile.isDirectory()) {
            for(File f: dirOrFile.listFiles()) {
                result.putAll(getLinks(f));
            }
            return result;
        }
        else {
            Path p = dirOrFile.toPath();
            int lastDot = p.toString().lastIndexOf(".");
            if(lastDot == -1 || !p.toString().substring(lastDot).equals(".md")) {
                return result;
            }
            ArrayList<String> links = getLinks(Files.readString(p));
            result.put(dirOrFile.getPath(), links);
            return result;
        }
    }

   public static ArrayList<String> getLinks(String markdown) {
    ArrayList<String> toReturn = new ArrayList<>();
    // find the next [, then find the ], then find the (, then read link upto next )
    int currentIndex = 0;

    if (!markdown.contains("[") && !markdown.contains("]") || (!markdown.contains(".com") && !markdown.contains(".net")))  {
        return toReturn;
    }
    while (currentIndex < markdown.lastIndexOf(")")) {
        int openBracket = markdown.indexOf("[", currentIndex);
        int closeBracket = markdown.indexOf("]", openBracket);
        int openParen = markdown.indexOf("(", closeBracket);
        int closeParen = markdown.indexOf(")", openParen);
        if (markdown.indexOf("!") + 1 == openBracket) {
            return toReturn;
        }
        if (openParen == closeParen || openParen != closeBracket + 1) {
            return toReturn;
        }
        toReturn.add(markdown.substring(openParen + 1, closeParen));
        currentIndex = closeParen + 1;
    }
    return toReturn;
}

    public static void main(String[] args) throws IOException {

        File content = new File(args[0]);
        Map<String, List<String>> links = getLinks(content);
	    System.out.println(links);
    }
}
   /* public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then read link upto next )
        int currentIndex = 0;
        while(currentIndex < markdown.lastIndexOf(")")) {
            int openBracket = markdown.indexOf("[", currentIndex);
            if (markdown.substring(openBracket - 1, openBracket).equals("!")){
                int closeBracket = markdown.indexOf("]", openBracket);
                int openParen = markdown.indexOf("(", closeBracket);
                int closeParen = markdown.indexOf(")", openParen);
                currentIndex = closeParen + 1;
            }
            else{
            int closeBracket = markdown.indexOf("]", openBracket);
             int openParen = markdown.indexOf("(", closeBracket);
             int closeParen = markdown.indexOf(")", openParen);
            toReturn.add(markdown.substring(openParen + 1, closeParen));
            currentIndex = closeParen + 1;
            }
      
        }

        return toReturn;
   }*/