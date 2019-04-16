package Main;
/**
 * @author tjc107 & jbr185
 */
public class SuperVillain extends Villain {

	private static MiniGameType[] minigames = {MiniGameType.DRGAME, MiniGameType.PSRGAME, MiniGameType.GNGAME};
	/**
	 * creates a villain stronger than any other with even with a preset name eviler than any other!
	 */
	public SuperVillain() {
		super("Evil McEvilFace", "Finally we meet!", 120, 120, 6, minigames, false);
	}
}
