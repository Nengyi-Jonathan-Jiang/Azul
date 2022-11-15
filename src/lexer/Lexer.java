package lexer;

import java.util.*;
import java.util.function.Function;
import java.util.regex.*;

public class Lexer {
    private List<TokenRule> tokenRules;

    public Lexer(){
        tokenRules = new LinkedList<>();
    }

    public void addRule(String name, String regex){
        tokenRules.add(new TokenRule(name, regex));
    }

    private static class TokenRule{
        public final String name;
        private final Pattern regex;
        public TokenRule(String name, String regex){
            this.name = name;
            this.regex = Pattern.compile(regex);
        }

        public int match(String input){
            Matcher m = regex.matcher(input);
            return m.find() && m.start() == 0 ? m.end() : -1;
        }
    }

    public Lex parse(String input){
        return new Lex(this, input);
    }

    public static class Lex{
        private String s;
        private int index;
        private final Lexer lexer;

        Lex(Lexer lexer, String input){
            this.lexer = lexer;
            s = input;
            index = 0;
        }

        public Token next(){
            if(s.length() == 0) return new Token("__END__","__END__", index);

            TokenRule rule = null;
            String sbstr = null;
            for(TokenRule tkr : lexer.tokenRules){
                int match = tkr.match(s);
                if(match != -1 && (sbstr == null || match > sbstr.length())){
                    rule = tkr;
                    sbstr = s.substring(0, match);
                }
            }

            if(rule != null){
                Token res = new Token(rule.name, sbstr, index);
                index += sbstr.length();
                s = s.substring(sbstr.length());
                return res;
            }
            index++;
            s = s.substring(1);
            return next();
        }
    }
}