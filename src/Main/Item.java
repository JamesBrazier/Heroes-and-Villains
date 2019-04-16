package Main;
/**
 * @author jbr185
 */
public abstract class Item{
	
	private String name = "nullItem";
	private int price;
	
	/**
	 * generates an undefined item
	 */
	public Item() {
	}
	/**
	 * generates an undefined item
	 * @param itemName the name of the item
	 * @param itemPrice the cost of the item
	 */
	public Item(String itemName, int itemPrice) {
		name = itemName;
		price = itemPrice;
	}
	
	public abstract void use(Hero hero);
	
	public abstract boolean canUse(Hero hero);
	
	public int getPrice() {
		return price;
	}
	/**
	 * returns the price altered by the modifier inputed
	 * @param modifier the value the price is multiplied by
	 * @return
	 */
	public int getPrice(double modifier) {
		double doublePrice = price;
		Double DoublePrice = doublePrice * modifier;
		return DoublePrice.intValue();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public void setPrice(int newPrice) {
		price = newPrice;
	}
	
	@Override
	public String toString() {
		return name + " (" + price + "gp)";
	}

}
