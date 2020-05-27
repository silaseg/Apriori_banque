package testfdd2;
import java.util.ArrayList;

public class ItemSet {
	
	private ArrayList<String> items = new ArrayList<String>();
	private double support = 0.0;
	
	public ItemSet(){
		
	}
	
	public ItemSet(ArrayList<String> items){
		
		this.items = items;
	}
	
	public ArrayList<String> getItems(){
		
		return this.items;
	}
	
	public double getSupport(){
		
		return this.support;
	}
	
	public void setSupport(double support){
		
		this.support = support;
	}
	
	public static void calculateSupport(ItemSet itemSetParameter, ArrayList<ItemSet> DTransactionalTable){
		
		double support = 0.0;
		ArrayList<String> selfItems = itemSetParameter.getItems();
		
		for(int i=0; i<Main.size; i++){
			
			ItemSet itemSet = DTransactionalTable.get(i);
			ArrayList<String> items = itemSet.getItems();
			
			boolean exists = true;
			for(int j=0; j<selfItems.size(); j++){
				
                                if(!(items.contains(selfItems.get(j)))) { exists = false; break;}
			}
			
			if(exists) support++;
		}
		
		support = support / Main.size;
		
		itemSetParameter.setSupport(support);
	}
	
	public static boolean isFrequent(ItemSet itemSetParameter, ArrayList<ItemSet> DTransactionalTable, double minSupport){
		
		ItemSet.calculateSupport(itemSetParameter, DTransactionalTable);
		if(itemSetParameter.getSupport() >= minSupport) return true;
		else return false;
	}
        
}
