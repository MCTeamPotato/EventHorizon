package de.dafuqs.revelationary.api.advancements;

import de.dafuqs.revelationary.advancement_criteria.AdvancementCountCriterion;
import de.dafuqs.revelationary.advancement_criteria.AdvancementGottenCriterion;
import de.dafuqs.revelationary.advancement_criteria.HadRevelationCriterion;
import de.dafuqs.revelationary.mixin.CriteriaAccessor;

public class AdvancementCriteria {
	
	/**
	 * Triggered every time a player gets a new advancement
	 */
	public static AdvancementGottenCriterion ADVANCEMENT_GOTTEN;
	/**
	 * Triggered every time a player gets a new advancement
	 * matches multiple advancements with optional count parameter
	 */
	public static AdvancementCountCriterion ADVANCEMENT_COUNT;
	/**
	 * Triggers every time a new block is revealed
	 */
	public static HadRevelationCriterion HAD_REVELATION;
	
	public static void register() {
		ADVANCEMENT_GOTTEN = CriteriaAccessor.callRegister(new AdvancementGottenCriterion());
		ADVANCEMENT_COUNT = CriteriaAccessor.callRegister(new AdvancementCountCriterion());
		HAD_REVELATION = CriteriaAccessor.callRegister(new HadRevelationCriterion());
	}
	
}