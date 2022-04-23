//https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

// we could count to the last index but this breaks if there is normal text after
// we have to count the number of open parenthesis and match it with the number of closed parenthesis
// but what if there is a open and close parenthesis after 
public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) {
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
            else if (markdown.indexOf("]") + 1 != markdown.indexOf("(")){
                int closeBracket = markdown.indexOf("]", openBracket);
                System.out.println("HELLO!");
                currentIndex = closeBracket+1;
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
   }


    public static void main(String[] args) throws IOException {
        Path fileName = Path.of(args[0]);
        String content = Files.readString(fileName);
        ArrayList<String> links = getLinks(content);
	    System.out.println(links);
    }
}
