package org.bm.cookbook.gui.frames;

import org.bm.cookbook.gui.Messages;

@SuppressWarnings("serial")
public class CookbookFrame extends CookbookInternalFrame {

	public CookbookFrame() {
	}

	@Override
	protected void createGui() {
		this.setTitle(Messages.getString("CookbookFrame.itemCookbook"));

	}

	@Override
	protected void reset() {
		// TODO Auto-generated method stub
		
	}

}
