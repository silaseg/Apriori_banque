package testfdd2;

import java.util.ArrayList;

public class associationRulesGenerator{
	
	  private ArrayList<ItemSet> DTransactionalTable;
	  private double minSupport;
	  private double minConfiance;
	  
	  public associationRulesGenerator(ArrayList<ItemSet> DTransactionalTable, double minSupport, double minConfiance){
		
		  this.DTransactionalTable = DTransactionalTable;
		  this.minSupport = minSupport;
		  this.minConfiance = minConfiance;
	  }
	  
	  public ArrayList<Regle> launchAprioriAlgorithm(){
		  
                if(DTransactionalTable != null){
                      return aprioriAlgorithm(DTransactionalTable, minSupport, minConfiance);    
                }
                return null;	      
	  }
	  
          private ArrayList<Regle> aprioriAlgorithm(ArrayList<ItemSet> DTransactionalTable, double minSupport, double minConfiance){
                    
                    ArrayList<ArrayList<ItemSet>> frequentItemSets = generateFrequentItemSets(DTransactionalTable, minSupport);
                    
                    ArrayList<Regle> solideRAs = getSolideRAs(generateRAs(frequentItemSets, DTransactionalTable, minConfiance), DTransactionalTable, minConfiance);
        
                    return solideRAs;
          }
            
	  private ArrayList<Regle> getSolideRAs(ArrayList<Regle> associationRules, ArrayList<ItemSet> DTransactionalTable, double minConfiance){
		  
		  ArrayList<Regle> solideRAs = new ArrayList<Regle>();
		  for(int i=0; i<associationRules.size(); i++){
			  
			  Regle regle = associationRules.get(i);
			  if(Regle.isSolide(regle, DTransactionalTable, minConfiance)) solideRAs.add(regle);
		  }
		  
		  return solideRAs;
	  }
    
        private ArrayList<Regle> generateRAs(ArrayList<ArrayList<ItemSet>> frequentItemSets, ArrayList<ItemSet> DTransactionalTable, double minConfiance){
                
                ArrayList<Regle> associationRules = new ArrayList<Regle>();
                
            int numFrequentItemSets = frequentItemSets.size();
            
                for(int i=0; i<numFrequentItemSets; i++){
                        
                        ArrayList<ItemSet> subFrequentItemSets = frequentItemSets.get(i);
                    
                    int numSubFrequentItemSets = subFrequentItemSets.size();
                                  
                        if(getK(subFrequentItemSets) >= 2){
                                
                                for(int j=0; j<numSubFrequentItemSets; j++){
                                        
                                        ItemSet frequentItemSet = subFrequentItemSets.get(j);
                                        ArrayList<ItemSet> H1 = getH1ItemSets(frequentItemSet);
                                        
                                        int k=0;
                                        ItemSet.calculateSupport(frequentItemSet, DTransactionalTable);
                                                                    
                                    
                                        while(k < H1.size()){
                                                
                                            ItemSet h1 = H1.get(k);
                                                    
                                                    ItemSet shortFrequentItemSet = minus(frequentItemSet, h1);
                                                    ItemSet.calculateSupport(shortFrequentItemSet, DTransactionalTable);
                                                    
                                                    double confiance = frequentItemSet.getSupport() / shortFrequentItemSet.getSupport();
                                                    if(confiance >= minConfiance){
                                                            
                                                            Regle r = new Regle(shortFrequentItemSet, h1);
                                                            associationRules.add(r);
                                                            k++;
                                                    }else{
                                                            
                                                            H1.remove(h1);
                                                    }
                                        }
                                        
                                        genRegles(frequentItemSet, H1, associationRules, minConfiance, DTransactionalTable);
                                }
                        }
                }
                
                return associationRules;
          }
	  
	  private void genRegles(ItemSet LkItemSet, ArrayList<ItemSet> Hm, ArrayList<Regle> R, double minConfiance, ArrayList<ItemSet> DTransactionalTable){
		  
		  int k = LkItemSet.getItems().size();
		  int m = getM(Hm);
		  
		  if(k > m+1){
			  
			  ArrayList<ItemSet> HmPlus1 = aprioriGenForHm(Hm);
			  
			  int i=0;
			  while(i < HmPlus1.size()){
				  
				  ItemSet.calculateSupport(LkItemSet, DTransactionalTable);
				  ItemSet.calculateSupport(minus(LkItemSet, HmPlus1.get(i)), DTransactionalTable);
				  
				  double confiance = LkItemSet.getSupport() / minus(LkItemSet, HmPlus1.get(i)).getSupport();
				  if(confiance >= minConfiance){
					  
					  Regle r = new Regle(minus(LkItemSet, HmPlus1.get(i)), HmPlus1.get(i));
					  R.add(r);
					  i++;
				  }else{
					  
					  HmPlus1.remove(HmPlus1.get(i));
				  }
			  }
			  
			  genRegles(LkItemSet, HmPlus1, R, minConfiance, DTransactionalTable);
		  }
	  }
          
          private ArrayList<ItemSet> H1Classe(ArrayList<ItemSet> H1){
              
              ArrayList<ItemSet> h1Classe = new ArrayList<ItemSet>();
              
              int numH1 = H1.size();
              for(int i=0; i < numH1; i++){
                  
                  ItemSet h1 = H1.get(i);
                  if(h1.getItems().get(0).contains("CLASSE")) h1Classe.add(h1);
              }
              
              return h1Classe;
          }
          
          private boolean containsClass(ItemSet itemSet){
              
              ArrayList<String> items = itemSet.getItems();
              int numItemSet = itemSet.getItems().size();
              
              for(int i=0; i<numItemSet; i++){                  
                  if( items.get(i).contains("CLASS")) return true;                   
              }
              return false;              
          }
          
          private ItemSet minus(ItemSet LkItemSet, ItemSet h){
		  
		  ArrayList<String> LkItems = LkItemSet.getItems();
		  ArrayList<String> hItems = h.getItems();
		  ArrayList<String> remainItems = new ArrayList<String>();
		  
		  for(int i=0; i<LkItems.size(); i++)
			  remainItems.add(LkItems.get(i));
		  
		  for(int i=0; i<hItems.size(); i++){
			  
			  if(remainItems.contains(hItems.get(i)))
				  remainItems.remove(hItems.get(i));
		  }
		  
		  return new ItemSet(remainItems);
	  }
	  
	  private ArrayList<ItemSet> aprioriGenForHm(ArrayList<ItemSet> Hm){
		  
		  ArrayList<ItemSet> HmPlus1 = selfJoin(Hm);
		  return HmPlus1;
	  }
	  
	  private ArrayList<ItemSet> getH1ItemSets(ItemSet LkItemSet){
		  
		  ArrayList<ItemSet> H1ItemSets = new ArrayList<ItemSet>();
		  ArrayList<String> LkItems = LkItemSet.getItems();
		  
		  for(int i=0; i<LkItems.size(); i++){
			  
			  ArrayList<String> items = new ArrayList<String>();
			  items.add(LkItems.get(i));
			  H1ItemSets.add(new ItemSet(items)); 
		  }
		  
		  return H1ItemSets;
	  }
	  
	  private int getM(ArrayList<ItemSet> Hm){
		  
		  return Hm.get(0).getItems().size();
	  }
	  
	  private int getK(ArrayList<ItemSet> Lk){
		  
		  return Lk.get(0).getItems().size();
	  }
	  
	  private ArrayList<ArrayList<ItemSet>> generateFrequentItemSets(ArrayList<ItemSet> DTransactionalTable, double minSupport){
		  
		  ArrayList<ArrayList<ItemSet>> frequentItemSets = new ArrayList<ArrayList<ItemSet>>();		  
		  ArrayList<ItemSet> L1 = getLkItemSets(getC1ItemSets(DTransactionalTable), DTransactionalTable, minSupport); 
	  
		  ArrayList<ItemSet> Ck;
		  ArrayList<ItemSet> Lk_1 = L1;
		  ArrayList<ItemSet> Lk;
		  
		  while(!(Lk_1.isEmpty())){
			  
			  frequentItemSets.add(Lk_1);
			  Ck = aprioriGen(Lk_1);
			  Lk = getLkItemSets(Ck, DTransactionalTable, minSupport);
			  Lk_1 = Lk;
		  }
		  
		  return frequentItemSets;
	  }
	  
	  private ArrayList<ItemSet> aprioriGen(ArrayList<ItemSet> Lk_1){
		 
		  return elagage(selfJoin(Lk_1), Lk_1);
	  }
	  
	  private ArrayList<ItemSet> elagage(ArrayList<ItemSet> selfJoinedLk_1, ArrayList<ItemSet> Lk_1){
		  
		  ArrayList<ItemSet> elagageResult = new ArrayList<ItemSet>();
		  
		  for(int i=0; i<selfJoinedLk_1.size(); i++){
			  
			  ArrayList<ItemSet> k_1_SubItemSets = getK_1_SubItemSets(selfJoinedLk_1.get(i));
			  boolean takeIt = true;
			  
			  for(int j=0; j<k_1_SubItemSets.size(); j++){
				  
				  boolean existsInLk_1 = false;
				  ArrayList<String> k_1_SubItems = k_1_SubItemSets.get(j).getItems();
				  
				  for(int k=0; k<Lk_1.size(); k++){
					  
					  ArrayList<String> Lk_1_Items = Lk_1.get(k).getItems();
					  boolean tempo = true;
					  for(int l=0; l<k_1_SubItems.size(); l++){
						  
                                              if(!(Lk_1_Items.contains(k_1_SubItems.get(l)))) {tempo = false; break;}
					  }
                                                                
                                          if(tempo){existsInLk_1 = tempo; break;}
				  }
                                
                                  if(!existsInLk_1) {takeIt = false; break;}
			  }
			  
			  if(takeIt) elagageResult.add(selfJoinedLk_1.get(i));
		  }
		  
		  return elagageResult;
	  }
	  
	  private ArrayList<ItemSet> getK_1_SubItemSets(ItemSet k_itemSet){
		  
		  ArrayList<ItemSet> subItemSets = new ArrayList<ItemSet>();
		  ArrayList<String> items = k_itemSet.getItems();
		  
                  int numItems = items.size();
		  for(int i=0; i<numItems; i++){
			  
			  ArrayList<String> subItems = new ArrayList<String>();
			  for(int j=0; j<numItems; j++){
				  
				  if(j != i) subItems.add(items.get(j));
			  }
			  ItemSet subItemSet = new ItemSet(subItems);
			  subItemSets.add(subItemSet);
		  }
		  
		  return subItemSets;
	  }
	  
	  private ArrayList<ItemSet> selfJoin(ArrayList<ItemSet> LkItemSets){
		  
		  ArrayList<ItemSet> selfJoinedLk = new ArrayList<ItemSet>();
		  
                  int numLkItemSets = LkItemSets.size();
		  
                  if(numLkItemSets > 1){
			  
			  for(int i=0; i<numLkItemSets; i++){
				  
				  for(int j=i+1; j<numLkItemSets; j++){
					  					  
					  ArrayList<String> items1 = LkItemSets.get(i).getItems();
					  ArrayList<String> items2 = LkItemSets.get(j).getItems();
					  
					  boolean samePrefix = true;
					  for(int k=0; k<items1.size()-1; k++){
						  
                                              if(!(items1.get(k).equals(items2.get(k)))){ samePrefix = false; break;}
					  }
					  
					  if(samePrefix){
						  
						  ArrayList<String> resultItems = new ArrayList<String>();
						  for(int k=0; k<items1.size()-1; k++)
							  resultItems.add(items1.get(k));
						  
						  resultItems.add(items1.get(items1.size()-1));
						  resultItems.add(items2.get(items2.size()-1));
						  ItemSet itemSet = new ItemSet(resultItems);
						  selfJoinedLk.add(itemSet);
					  }
				  }
			  }
		  }
		  	  
		  return selfJoinedLk;
	  }
	  
	  private ArrayList<ItemSet> getLkItemSets(ArrayList<ItemSet> CkItemSets, ArrayList<ItemSet> DTransactionalTable, double minSupport){
		
		  ArrayList<ItemSet> LkItemSets = new ArrayList<ItemSet>();
		  
		  for(int i=0; i<CkItemSets.size(); i++){
			  
			  ItemSet itemSetCk = CkItemSets.get(i);
			  if(ItemSet.isFrequent(itemSetCk, DTransactionalTable, minSupport)) LkItemSets.add(itemSetCk);
		  }

		  return LkItemSets;
	  }
	  
	  private ArrayList<ItemSet> getC1ItemSets(ArrayList<ItemSet> DTransactionalTable){
		  
		  ArrayList<ItemSet> C1ItemSets = new ArrayList<ItemSet>();
		  
		  for(int i=0; i<Main.size; i++){
			  
			  ArrayList<String> items = DTransactionalTable.get(i).getItems();
			  
			  for(int j=0; j<items.size(); j++){
				  
				  boolean exists = false;
				  for(int k=0; k<C1ItemSets.size(); k++){
					  
					  ArrayList<String> itemsC1 = C1ItemSets.get(k).getItems();
					  for(int l=0; l<itemsC1.size(); l++){
						  
						  if(itemsC1.get(l).equals(items.get(j))) exists = true;
					  }  
				  }
				  if(!exists){
					  
					  ItemSet itemSet = new ItemSet();
					  itemSet.getItems().add(items.get(j));
					  C1ItemSets.add(itemSet);
				  }
			  }			    
		  }
		  
		  return C1ItemSets;
	  }
}
