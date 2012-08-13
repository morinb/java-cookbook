package org.bm.cookbook.gui.frames;

import org.bm.cookbook.gui.Messages;

@SuppressWarnings("serial")
public class RecipeFrame extends CookbookInternalFrame {

	public RecipeFrame() {
	}

	@Override
	protected void createGui() {
		this.setTitle(Messages.getString("CookbookFrame.itemRecipe"));

	}

	@Override
	protected void reset() {
		// TODO Auto-generated method stub
		
	}
}
