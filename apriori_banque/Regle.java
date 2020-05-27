package testfdd2;

import java.util.ArrayList;

public class Regle {
	
	private ItemSet antecedent;
	private ItemSet consequence;
	private double confiance;
	
	public Regle(ItemSet antecedent, ItemSet consequence){
		
		this.antecedent = antecedent;
		this.consequence = consequence;
	}
	
	public Regle(){
		
	}
	
	public ItemSet getAntecedent(){
		
		return this.antecedent;
	}
	
	public ItemSet getConsequence(){
		
		return this.consequence;
	}
	
	public double getConfiance(){
		
		return this.confiance;
	}
	
	public void setAntecedent(ItemSet antecedent){
		
		this.antecedent = antecedent;
	}
	
	public void setConsequence(ItemSet consequence){
		
		this.consequence = consequence;
	}
	
	public void setConfiance(double confiance){
		
		this.confiance = confiance;
	}
	
	public static void calculateConfiance(Regle regle, ArrayList<ItemSet> DTransactionalTable){
		
		ItemSet antecedent = regle.getAntecedent();
		ItemSet consequence = regle.getConsequence();
		
		ArrayList<String> allItems = new ArrayList<String>();
		ArrayList<String> antecedentItems = antecedent.getItems();
		ArrayList<String> consequenceItems = consequence.getItems();
		
		for(int i=0; i<antecedentItems.size(); i++)
			allItems.add(antecedentItems.get(i));
		
		for(int j=0; j<consequenceItems.size(); j++)
			allItems.add(consequenceItems.get(j));
		
		ItemSet fullItemSet = new ItemSet(allItems);
		
		ItemSet.calculateSupport(fullItemSet, DTransactionalTable);
		ItemSet.calculateSupport(antecedent, DTransactionalTable);
		
		double confiance = fullItemSet.getSupport() / antecedent.getSupport();
		regle.setConfiance(confiance);
	}
	
	public static boolean isSolide(Regle regle, ArrayList<ItemSet> DTransactionalTable, double minConfiance){
		
		Regle.calculateConfiance(regle, DTransactionalTable);
		
		if(regle.getConfiance() >= minConfiance) return true;
		else return false;
	}
	
    public String toStringDetails(){
                
            String regleString = "";
            ArrayList<String> antecedentItems = this.getAntecedent().getItems();
            ArrayList<String> consequenceItems = this.getConsequence().getItems();
            
            int i;
            for(i=0; i<antecedentItems.size()-1; i++){
                    
                
                regleString += Main.getCommentField(antecedentItems.get(i))+": ";
                regleString += antecedentItems.get(i)+", ";
                
            }
        
            regleString += Main.getCommentField(antecedentItems.get(i))+": ";
            regleString += antecedentItems.get(i);
    
            String valSupLeft = String.valueOf(this.getAntecedent().getSupport()).substring(0,3);          
            regleString += "   sup:("+valSupLeft+")";
        
            regleString += " ==> ";
                    
            for(i=0; i<consequenceItems.size()-1; i++){
                    
                regleString += Main.getCommentField(consequenceItems.get(i))+": ";
                regleString += consequenceItems.get(i)+", ";
            }
            
            regleString += Main.getCommentField(consequenceItems.get(i))+": ";
            regleString += consequenceItems.get(i);
        
            String valConf = String.valueOf(this.getConfiance()).substring(0,3);
        
            regleString += "   conf:("+valConf+")";
                    
            return regleString;             
    }

    public String toString(){
            
            String regleString = "";
            ArrayList<String> antecedentItems = this.getAntecedent().getItems();
            ArrayList<String> consequenceItems = this.getConsequence().getItems();
            
            int i;
            for(i=0; i<antecedentItems.size()-1; i++){
                    
                    regleString += antecedentItems.get(i)+", ";
            }
        
            regleString += antecedentItems.get(i);
    
            
            regleString += "  ==>  ";
                    
            for(i=0; i<consequenceItems.size()-1; i++){
                    
                    regleString += consequenceItems.get(i)+", ";
            }
            regleString += consequenceItems.get(i);
        
                    
            return regleString;             
    }
}
