package exercise5;

import java.util.HashSet;

public class DNA{
    private static final int CODON_LENGTH =3;
    private static final String START_CODON= "ATG";
    private static final String END_CODON_1= "TAA";
    private static final String END_CODON_2= "TAG";
    private static final String END_CODON_3= "TGA";
    private String sequence;
    private String junkRemoved;

    public DNA (String sequence){
        this.sequence=sequence;
        if (!isValid()) {
            throw new IllegalArgumentException("Invalid DNA sequence");
        }
    }
    public boolean isProtein(){
        return startCorrect()&& endCorrect()&&massCorrect()&&codonCountCorrect();
    }
    public boolean startCorrect(){
        return junkRemoved.substring(0,CODON_LENGTH).equals(START_CODON);
    }
    public boolean endCorrect(){
        return (junkRemoved.substring(junkRemoved.length()-CODON_LENGTH, junkRemoved.length()).equals(END_CODON_1 ) )
                || (junkRemoved.substring(junkRemoved.length()-CODON_LENGTH, junkRemoved.length()).equals(END_CODON_2 )
                || (junkRemoved.substring(junkRemoved.length()-CODON_LENGTH, junkRemoved.length()).equals(END_CODON_3)));
    }
    public boolean isValid(){
        removeJunk();
        return junkRemoved.length()%3==0 && !sequence.equals("") && sequence.length()!=0;
    }
    public void removeJunk(){
        StringBuilder sequence_builder= new StringBuilder(sequence);
        int i=0;
        System.out.println(sequence_builder.length());
        while(i<sequence_builder.length()){
            if(!isNucleotide(sequence_builder.charAt(i))){
                sequence_builder.deleteCharAt(i);
            }
            else{
                i++;
            }
        }
        junkRemoved = sequence_builder.toString();
    }
    public boolean massCorrect(){
        int num_c= nucleotideCount('C');
        int num_g= nucleotideCount('G');
        double cg_mass= num_c * 111.103 + num_g * 151.128;
        if(cg_mass/totalMass()>= 0.3){
            return true;
        }
        return false;
    }
    public boolean codonCountCorrect(){
        return junkRemoved.length()/3 >=5;
    }
    public double totalMass(){
        double mass=0;
        for(int i=0; i<sequence.length(); i++){
            switch (sequence.charAt(i)){
                case 'A':
                    mass+=135.128;
                    break;
                case 'T':
                    mass+=125.107;
                    break;
                case 'C':
                    mass+=111.103;
                    break;
                case 'G':
                    mass+=151.128;
                    break;
                default:
                    mass+=100.000;
            }
        }
        return Math.round(mass*10)/10.0;
    }
    public int nucleotideCount(char c){
        if(c=='A' || c== 'T'|| c=='G'|| c=='C'){
            int count=0;
            StringBuilder sb= new StringBuilder((sequence));
            for(int i=0; i<sb.length(); i++){
                if(sb.charAt(i)==c){
                    count++;
                }
            }
            return count;
        }
        else{
            return 0;
        }

    }
    private boolean isNucleotide(char c){
        return c=='A' || c== 'T'|| c=='G'|| c=='C';
    }
    public HashSet<String> codonSet(){
        HashSet<String> distinctCodonSet= new HashSet<>();
        StringBuilder newcodon=new StringBuilder();
        int i=0;
        while(i+ CODON_LENGTH-1 <sequence.length()) {
            int j = i;
            int count = 0;
            newcodon = new StringBuilder();
            while (count < CODON_LENGTH && j < sequence.length()) {
                char candidate = sequence.charAt(j);
                if (isNucleotide(candidate)) {
                    newcodon.append(candidate);
                    count++;
                }
                j++;
            }
            if (j <= sequence.length() && count == CODON_LENGTH) {
                distinctCodonSet.add(newcodon.toString());
            }
            i = j;
        }
        return distinctCodonSet;
    }
    public boolean isCodon(String candidate){
        if(candidate.length()!= CODON_LENGTH){
            return false;
        }
        StringBuilder codonCandidate= new StringBuilder(candidate);
        for(int i=0; i< CODON_LENGTH; i++){
            if(!isNucleotide(codonCandidate.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public void mutateCodon(String originalCodon, String newCodon){
        if(isCodon(originalCodon)&& isCodon(newCodon)){
            StringBuilder sequence_builder= new StringBuilder(sequence);
            int i=0;
            System.out.println(sequence_builder.length());
            while(i<sequence_builder.length()){
                if(!isNucleotide(sequence_builder.charAt(i))){
                    sequence_builder.deleteCharAt(i);
                }
                else{
                    i++;
                }
            }
            for(int j=0; j<sequence_builder.length(); j+=3){
                if(sequence_builder.subSequence(j,j+CODON_LENGTH).equals(originalCodon)){
                    sequence_builder.replace(j,j+CODON_LENGTH, newCodon);
                }
            }
            this.sequence= sequence_builder.toString();
        }
    }
    public String sequence(){
        return this.sequence;
    }

}