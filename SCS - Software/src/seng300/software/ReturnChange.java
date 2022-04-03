package seng300.software;

import java.util.List;
import java.util.Map;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import org.lsmr.selfcheckout.devices.*;
import org.lsmr.selfcheckout.devices.observers.*;

//made by abhinav and alexanna

public class ReturnChange implements BanknoteSlotObserver, CoinTrayObserver {
	
	private SelfCheckoutStation scs;
	  
	public boolean danglingNote=false;
	
	public boolean ejecting=false;
	public boolean ejectingCoin=false;
	
	public int bankNotesEjected=0;
	public int coinsEjected=0;
	
	public boolean doneEjectingNote=false;
	public boolean doneEjectingCoin=false;
	
/*	variables used from scs
	
	int[] banknoteDenominations;
	List<BigDecimal> coinDenominations;
	
	Map<Integer, BanknoteDispenser> banknoteDispensers;
	Map<BigDecimal, CoinDispenser> coinDispensers;
*/
	private List<Integer> banknoteValues;
	private List<BigDecimal> coinValues;
	
	private Map<Integer, Integer> banknoteAmounts = new HashMap<Integer, Integer>();
	private Map<BigDecimal, Integer> coinAmounts = new HashMap<BigDecimal, Integer>();
	
	private Map<BigDecimal, Integer> partialChange = new HashMap<BigDecimal, Integer>();
	private Map<BigDecimal, ArrayList<ArrayList<Integer>>> partialBanknoteChange = new HashMap<BigDecimal, ArrayList<ArrayList<Integer>>>();
	private Map<BigDecimal, ArrayList<ArrayList<BigDecimal>>> partialCoinChange = new HashMap<BigDecimal, ArrayList<ArrayList<BigDecimal>>>();
	
	private ArrayList<Integer> banknoteChange = new ArrayList<Integer>();
	private ArrayList<BigDecimal> coinChange = new ArrayList<BigDecimal>();
	

	// ------------------------------------------------------------------
	// HAHA WHAT IF CHANGE CANNOT BE MADE LIKE THWRE ARE NO COINS?
	// ------------------------------------------------------------------
	
	public ReturnChange (SelfCheckoutStation scs, BigDecimal changevalue) {
		
		this.scs = scs;
    
	    //Attach observers 
	    scs.banknoteOutput.attach(this);
	    scs.coinTray.attach(this);
    
		//i am aware this is not efficient. 
		//i might change it idk.
		this.banknoteValues = new ArrayList<Integer>(scs.banknoteDenominations.length);
		for (int i : scs.banknoteDenominations)
		{
			this.banknoteValues.add(i);
			//make count of amount of banknotes
			this.banknoteAmounts.put(i, scs.banknoteDispensers.get(i).size());
		}
		
		this.coinValues = new ArrayList<BigDecimal>(scs.coinDenominations);
		//make count of amount of coins
		for (BigDecimal i : scs.coinDenominations)
		{
			this.coinAmounts.put(i, scs.coinDispensers.get(i).size());
		}
		
		changevalue = changevalue.setScale(2, RoundingMode.UP);
		makeChange(changevalue);

		//haha what if change cannot be made?
		if (partialChange.get(changevalue) == Integer.MAX_VALUE) {

			System.out.println("Sorry for the inconvenience. Change for this value cannot be returned at this time!");
			//notify attendant? 
			return;
			
		}
		
		//then update the values :)
		banknoteChange = partialBanknoteChange.get(changevalue).get(0);
		coinChange = partialCoinChange.get(changevalue).get(0);
		
	}
  
	//Function for ejecting banknotes
    public void ejectBanknote() throws EmptyException, DisabledException, OverloadException {
    	

    	ArrayList<Integer> noteChange = banknoteChange;
        
        //Sets the thing to ejecting, which tells the program that we haven't finished giving out all the change owed
        this.ejecting=true;
        
        //Emit the current index of change left if its possible
        if (bankNotesEjected<noteChange.size()) {
            scs.banknoteDispensers.get(noteChange.get(bankNotesEjected)).emit();
            bankNotesEjected++;
        } 
        
        else {
            //If the ejected amount is greater than or equal to how much change we need to return, then we have succesfully ejected everything
            ejecting=false;
            doneEjectingCoin=true;
        }
    }

    //Function for ejecting Coins
    public boolean ejectCoin() {
        
        //Sets the thing to ejecting, which tells the program that we haven't finished giving out all the change owed
        this.ejectingCoin=true;
        
        //This will keep looping until the entire change amount has been dispensed returning true OR the sink needs to be emptied, at which point it will return false
        while (scs.coinTray.hasSpace()){
        	
            if (coinsEjected<coinChange.size()){
                //This means we have more to eject
                try {
                    scs.coinDispensers.get(coinChange.get(coinsEjected)).emit();
                    coinsEjected++;
                    //return doneEjectingCoin;
                }
                catch(Exception e) {
                    //This should NEVER happen
                    //return doneEjectingCoin;
                }
            }
            else {
                //This means that we have ejected everything
                ejectingCoin=false;
                doneEjectingCoin=true;
                return doneEjectingCoin;
            }
            
        }
        
        //Deal with overload exception stuff, make sure the user removes the coin
        return doneEjectingCoin;
    }
    

    @Override
    public void banknoteRemoved(BanknoteSlot slot) {
    	
        danglingNote=false;
        
        if (ejecting==true) {
            try {
            	this.ejectBanknote();
            }
            catch(Exception e){
            }
        }
    }
  
	//uses a dynamic programming algorithm to identify the banknotes/coins to return
	//also ensures there are enough of the banknotes/coins before it returns it as a solution
	private void makeChange(BigDecimal changevalue) {
		
		int i = changevalue.compareTo(BigDecimal.ZERO);
		
		//invalid solution
		if(i < 0) {
			partialChange.put(changevalue, Integer.MAX_VALUE);
			return;
		} 
	
		//valid solution
		else if (i == 0) {
			partialChange.put(changevalue, 0);
			
			//empty array lists if no change is owed so we don't cause an error :)
			ArrayList<ArrayList<Integer>> solutionB = new ArrayList<ArrayList<Integer>>();
			ArrayList<ArrayList<BigDecimal>> solutionC = new ArrayList<ArrayList<BigDecimal>>();
			
			ArrayList<Integer> b = new ArrayList<Integer>();
			solutionB.add(b);
			
			ArrayList<BigDecimal> c = new ArrayList<BigDecimal>();
			solutionC.add(c);
			
			partialBanknoteChange.put(changevalue, solutionB);
			partialCoinChange.put(changevalue, solutionC);
			return;
		}
		
		//recursive case
		else {
			
			//solution has already been calculated
			if (partialChange.containsKey(changevalue) && partialChange.get(changevalue) != Integer.MAX_VALUE - 1) {
				
				return;
			}
			
			//calculate solution for this change value and then update partialChange maps
			else {
				
				int minPartialBanknote = Integer.MAX_VALUE;
				int minPartialCoin = Integer.MAX_VALUE;
				int partialBanknote;
				int partialCoin;
				boolean sol = false;
				

				// --------------------------------------
				// check banknotes
				// --------------------------------------
				
				Integer optimalBanknote = null;
				
				//calculate all partial solutions of changevalue - (a banknotevalue)
				for(Integer bv : banknoteValues) {
					

					//check if there is one
					if(banknoteAmounts.get(bv) < 1) {
						continue;
					}
					
					//recurse
					BigDecimal partialMC = (changevalue.subtract(BigDecimal.valueOf(bv))).setScale(2, RoundingMode.UP);
					makeChange(partialMC);


					//check if it was an invalid solution
					if(partialChange.get(partialMC) == Integer.MAX_VALUE) {
						continue;
					}
					
					//check if it was a sol situation
					if(partialChange.get(partialMC) == Integer.MAX_VALUE - 1) {
						sol = true;
						continue;
					}
					
					//check if it was a valid full solution
					if(partialChange.get(partialMC) == 0) {
						
						//this is optimal
						ArrayList<ArrayList<Integer>> solutionB = new ArrayList<ArrayList<Integer>>();
						ArrayList<ArrayList<BigDecimal>> solutionC = new ArrayList<ArrayList<BigDecimal>>();
						ArrayList<Integer> b = new ArrayList<Integer>();
						b.add(bv);
						solutionB.add(b);
						ArrayList<BigDecimal> c = new ArrayList<BigDecimal>();
						solutionC.add(c);
						partialBanknoteChange.put(changevalue, solutionB);
						partialCoinChange.put(changevalue, solutionC);
						partialChange.put(changevalue, 1);
						optimalBanknote = bv;
						return;
					}
					
					//or see how it compares to the current optimal case
					partialBanknote = partialBanknoteChange.get(partialMC).get(0).size();
					partialCoin = partialCoinChange.get(partialMC).get(0).size();
					
					//check if this is more optimal than the current optimal
					if ( partialCoin < minPartialCoin || 
						(partialCoin == minPartialCoin && partialBanknote < minPartialBanknote) ) {
						
						ArrayList<ArrayList<Integer>> solutionB = new ArrayList<ArrayList<Integer>>();
						ArrayList<ArrayList<BigDecimal>> solutionC = new ArrayList<ArrayList<BigDecimal>>();
						
						//check if there are enough coins in any of the partial solutions to add the cv
						int s = 0;
						for(ArrayList<Integer> partialList : partialBanknoteChange.get(partialMC)) {
							int c = 1;
							for(Integer bvc : partialList) {
								if (bv.compareTo(bvc) == 0) {
									c++;
								}
							}
							//if there are add to possible solutions
							if (banknoteAmounts.get(bv) - c >= 0) {
								ArrayList<Integer> bvPartialList = new ArrayList<Integer>(partialList);
								bvPartialList.add(bv);
								solutionB.add(bvPartialList);
								solutionC.add(partialCoinChange.get(partialMC).get(s));
							}
							s++;
						}
						
						//if there aren't any then we are sol. move on to next value
						if (solutionB.size() == 0) {
							
							//shit outta luck
							sol = true;
							continue;
						}
						
						//overwrite existing partial solution
						partialBanknoteChange.put(changevalue, solutionB);
						partialCoinChange.put(changevalue, solutionC);
						partialChange.put(changevalue, partialBanknote + partialCoin + 1);
						
						//update the min number of coins
						minPartialBanknote = partialBanknote;
						minPartialCoin = partialCoin;
						
						optimalBanknote = bv;
						
					//check if this is as optimal as the current optimal
					} else if (partialCoin == minPartialCoin && partialBanknote == minPartialBanknote) {
						
						ArrayList<ArrayList<Integer>> solutionB = new ArrayList<ArrayList<Integer>>();
						ArrayList<ArrayList<BigDecimal>> solutionC = new ArrayList<ArrayList<BigDecimal>>();
						
						//check if there are enough coins in any of the partial solutions to add the cv
						int s = 0;
						for(ArrayList<Integer> partialList : partialBanknoteChange.get(partialMC)) {
							int c = 1;
							for(Integer bvc : partialList) {
								if (bv.compareTo(bvc) == 0) {
									c++;
								}
							}
							//if there are add to possible solutions
							if (banknoteAmounts.get(bv) - c >= 0) {
								ArrayList<Integer> bvPartialList = new ArrayList<Integer>(partialList);
								bvPartialList.add(bv);
								solutionB.add(bvPartialList);
								solutionC.add(partialCoinChange.get(partialMC).get(s));
							}
							s++;
						}
						
						//if there aren't any then the existing optimal is optimal
						if (solutionB.size() == 0) {
							continue;
						}
						
						//append to existing partial solutions
						ArrayList<ArrayList<Integer>> existingSolutionB = partialBanknoteChange.get(changevalue);
						ArrayList<ArrayList<BigDecimal>> existingSolutionC = partialCoinChange.get(changevalue);

						existingSolutionB.addAll(solutionB);
						existingSolutionC.addAll(solutionC);
						
						//then put them back? idk how java works are these pointers??
						partialBanknoteChange.put(changevalue, existingSolutionB);
						partialCoinChange.put(changevalue, existingSolutionC);
						
						//update the min number of coins
						minPartialBanknote = partialBanknote;
						minPartialCoin = partialCoin;
						
					}
				}

				//i have decided if an optimal banknote exists that is more optimal than any coin
				//check for optimal banknote to update this changevalue's partial solution
				if(optimalBanknote != null) {
					
					return;
				}
				
				// --------------------------------------
				// check coins
				// --------------------------------------
				
				BigDecimal optimalCoin = null;
				
				//calculate all partial solutions of changevalue - (a coinvalue)
				for(BigDecimal cv : coinValues) {
					
					//check if there is one
					if(coinAmounts.get(cv) < 1) {
						continue;
					}
					
					//recurse
					BigDecimal partialMC = (changevalue.subtract(cv)).setScale(2, RoundingMode.UP);
					makeChange(partialMC);

					//check if it was an invalid solution
					if(partialChange.get(partialMC) == Integer.MAX_VALUE) {
						continue;
					}
					
					//check if it was a sol situation
					if(partialChange.get(partialMC) == Integer.MAX_VALUE - 1) {
						sol = true;
						continue;
					}
					
					//check if it was a valid full solution
					if(partialChange.get(partialMC) == 0) {
						
						//this is optimal
						ArrayList<ArrayList<Integer>> solutionB = new ArrayList<ArrayList<Integer>>();
						ArrayList<ArrayList<BigDecimal>> solutionC = new ArrayList<ArrayList<BigDecimal>>();
						ArrayList<Integer> b = new ArrayList<Integer>();
						solutionB.add(b);
						ArrayList<BigDecimal> c = new ArrayList<BigDecimal>();
						c.add(cv);
						solutionC.add(c);
						partialBanknoteChange.put(changevalue, solutionB);
						partialCoinChange.put(changevalue, solutionC);
						partialChange.put(changevalue, 1);
						optimalCoin = cv;
						return;
					}
					
					//or see how it compares to the current optimal case
					partialBanknote = partialBanknoteChange.get(partialMC).get(0).size();
					partialCoin = partialCoinChange.get(partialMC).get(0).size();
					
					//check if this is more optimal than the current optimal
					if ( partialCoin < minPartialCoin || 
						(partialCoin == minPartialCoin && partialBanknote < minPartialBanknote) ) {
						
						ArrayList<ArrayList<Integer>> solutionB = new ArrayList<ArrayList<Integer>>();
						ArrayList<ArrayList<BigDecimal>> solutionC = new ArrayList<ArrayList<BigDecimal>>();
						
						//check if there are enough coins in any of the partial solutions to add the cv
						int s = 0;
						for(ArrayList<BigDecimal> partialList : partialCoinChange.get(partialMC)) {
							int c = 1;
							for(BigDecimal cvc : partialList) {
								if (cv.compareTo(cvc) == 0) {
									c++;
								}
							}
							//if there are add to possible solutions
							if (coinAmounts.get(cv) - c >= 0) {
								ArrayList<BigDecimal> cvPartialList = new ArrayList<BigDecimal>(partialList);
								cvPartialList.add(cv);
								solutionC.add(cvPartialList);
								solutionB.add(partialBanknoteChange.get(partialMC).get(s));
							}
							s++;
						}
						
						//if there aren't any then we are sol. move on to next value
						if (solutionC.size() == 0) {
							
							//shit outta luck
							sol = true;
							continue;
						}
						
						//overwrite existing partial solution
						partialBanknoteChange.put(changevalue, solutionB);
						partialCoinChange.put(changevalue, solutionC);
						partialChange.put(changevalue, partialBanknote + partialCoin + 1);
						
						//update the min number of coins
						minPartialBanknote = partialBanknote;
						minPartialCoin = partialCoin;
						
						optimalCoin = cv;
						
					//check if this is as optimal as the current optimal
					} else if (partialCoin == minPartialCoin && partialBanknote == minPartialBanknote) {
						
						ArrayList<ArrayList<Integer>> solutionB = new ArrayList<ArrayList<Integer>>();
						ArrayList<ArrayList<BigDecimal>> solutionC = new ArrayList<ArrayList<BigDecimal>>();
						
						//check if there are enough coins in any of the partial solutions to add the cv
						int s = 0;
						for(ArrayList<BigDecimal> partialList : partialCoinChange.get(partialMC)) {
							int c = 1;
							for(BigDecimal cvc : partialList) {
								if (cv.compareTo(cvc) == 0) {
									c++;
								}
							}
							//if there are add to possible solutions
							if (coinAmounts.get(cv) - c >= 0) {
								ArrayList<BigDecimal> cvPartialList = new ArrayList<BigDecimal>(partialList);
								cvPartialList.add(cv);
								solutionC.add(cvPartialList);
								solutionB.add(partialBanknoteChange.get(partialMC).get(s));
							}
							s++;
						}
						
						//if there aren't any then the existing optimal is optimal
						if (solutionC.size() == 0) {
							continue;
						}
						
						//append to existing partial solutions
						ArrayList<ArrayList<Integer>> existingSolutionB = partialBanknoteChange.get(changevalue);
						ArrayList<ArrayList<BigDecimal>> existingSolutionC = partialCoinChange.get(changevalue);

						existingSolutionB.addAll(solutionB);
						existingSolutionC.addAll(solutionC);
						
						//then put them back? idk how java works are these pointers??
						partialBanknoteChange.put(changevalue, existingSolutionB);
						partialCoinChange.put(changevalue, existingSolutionC);
						
						//update the min number of coins
						minPartialBanknote = partialBanknote;
						minPartialCoin = partialCoin;
						
					}
				}
				
				//check for optimal coin to return this changevalue's partial solutions
				if(optimalCoin != null) {
					
					return;
				}
				
				//check for sol
				if(sol) {
					partialChange.put(changevalue, Integer.MAX_VALUE - 1);
					return;
				}
				
				//if there is no optimal banknote or optimal coin, then this is not a valid solution
				partialChange.put(changevalue, Integer.MAX_VALUE);
				return;
			}
		}
	}

	//overridden methods that we didn't use
	
	@Override
	public void enabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void disabled(AbstractDevice<? extends AbstractDeviceObserver> device) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void coinAdded(CoinTray tray) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknoteInserted(BanknoteSlot slot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void banknotesEjected(BanknoteSlot slot) {
		danglingNote = true;
		
	}
	

}
