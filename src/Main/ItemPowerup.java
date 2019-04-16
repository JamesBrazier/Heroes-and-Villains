package Main;
 /**
 * @author jbr185
 */
public class ItemPowerup extends Item {
	
	private Attribute attribute;
	
	/**
	 * generates a new powerup item with a random effect
	 */
	public ItemPowerup() {
		int randomEffect = Constants.random.nextInt(4);
		switch (randomEffect) {
			case 0:
				attribute = Attribute.MOREDAMAGE;
				setName("Increase Damage");
				break;
			case 1:
				attribute = Attribute.MOREMAXHEALTH;
				setName("Increase Max Health");
				break;
			case 2:
				attribute = Attribute.LESSRECOVERYTURNS;
				setName("Reduce Recovery Time");
				break;
			case 3:
				attribute = Attribute.MOREDEFENSE;
				setName("Increase Defence");
				break;
		}
		setPrice(Constants.powerupCost);
	}
	/**
	 * generates a powerup in the specified state
	 * @param itemName name of the item
	 * @param itemPrice cost of the item
	 * @param itemAttribute the effect of the item
	 */
	public ItemPowerup(String itemName, int itemPrice, Attribute itemAttribute) {
		super(itemName, itemPrice);
		attribute = itemAttribute;
	}

	@Override
	/**
	 * takes a hero and applies the effect to them
	 * @param hero the hero the effect is applied to
	 * @return returns false if the item cannot be used
	 */
	public void use(Hero hero) {
		switch (attribute) {
			case MOREDAMAGE:
				effectMoreDamage(hero);
				break;
			case MOREMAXHEALTH:
				effectMoreMaxHealth(hero);
				break;
			case LESSRECOVERYTURNS:
				effectLessRecoveryTurns(hero);
				break;
			case MOREDEFENSE:
				effectLessDamageTaken(hero);
				break;
		}
	}
	/**
	 * checks if the hero can increase the stat that the item increases
	 */
	public boolean canUse(Hero hero) {
		switch (attribute) {
		case MOREDAMAGE:
			if (hero.getDamage() < Constants.maxDamage) {
				return true;
			}
			return false;
		case MOREMAXHEALTH:
			if (hero.getMaxHealth() < Constants.maxHealth) {
				return true;
			}
			return false;
		case LESSRECOVERYTURNS:
			if (hero.getRecoveryRate() == Constants.defaultRecoveryRate) {
				return true;
			}
			return false;
		case MOREDEFENSE:
			if (hero.getDefense() < Constants.maxDefense) {
				return true;
			}
			return false;
		default:
			return false;
		}
	}
	/**
	 * increases the damage of the hero
	 * @param hero the hero the effect is applied to
	 * @return returns false if the item cannot be used
	 */
	private void effectMoreDamage(Hero hero) {
		hero.setDamage(hero.getDamage() + Constants.damagePerItem);
	}
	/**
	 * increases the max health of the hero
	 * @param hero the hero the effect is applied to
	 * @return returns false if the item cannot be used
	 */
	private void effectMoreMaxHealth(Hero hero) {
		hero.setMaxHealth(hero.getMaxHealth() + Constants.healthPerItem);
		hero.heal(Constants.healthPerItem);
	}
	/**
	 * reduces the time for the hero to apply healing
	 * @param hero the hero the effect is applied to
	 * @return returns false if the item cannot be used
	 */
	private void effectLessRecoveryTurns(Hero hero) {
		hero.setRecoveryRate(Constants.fastRecoveryRate);
	}
	/**
	 * reduces the amount of damage the hero takes
	 * @param hero the hero the effect is applied to
	 * @return returns false if the item cannot be used
	 */
	private void effectLessDamageTaken(Hero hero) {
		hero.setDefense(hero.getDefense() + Constants.defensePerItem);
	}

}
