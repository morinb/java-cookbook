package org.bm.cookbook.gui.frames;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.bm.cookbook.db.model.Cookbook;
import org.bm.cookbook.db.model.Model;
import org.bm.cookbook.db.model.Recipe;
import org.bm.cookbook.gui.Messages;
import org.bm.cookbook.gui.frames.renderers.HasImageListCellRenderer;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings("serial")
public class CookbookFrame extends CookbookInternalFrame {

	private JButton add;
	private JButton delete;
	private JButton update;
	private JTextField name;

	private JComboBox<Recipe> recipes;

	private JList<Cookbook> cookbooks;

	public CookbookFrame() {}

	@Override
	protected void createGui() {
		this.setTitle(Messages.getString("CookbookFrame.itemCookbook"));

		add = new JButton(Messages.getString("UnitFrame.buttonAdd")); //$NON-NLS-1$
		delete = new JButton(Messages.getString("UnitFrame.buttonDelete")); //$NON-NLS-1$
		update = new JButton(Messages.getString("UnitFrame.buttonUpdate")); //$NON-NLS-1$

		name = new JTextField();

		recipes = new JComboBox<>();
		recipes.setRenderer(new HasImageListCellRenderer(30));

		cookbooks = new JList<>();
		cookbooks.setCellRenderer(new HasImageListCellRenderer(30));

		FormLayout layout = new FormLayout("p:g, 4dlu, p:g, 4dlu, p:g", "p, 2dlu, p, 2dlu, f:p:g");

		layout.setColumnGroups(new int[][] { { 1, 3, 5 } });
		PanelBuilder pb = new PanelBuilder(layout);
		pb.border(Borders.DIALOG);

		pb.add(add, CC.xy(1, 1));
		pb.add(delete, CC.xy(3, 1));
		pb.add(update, CC.xy(5, 1));

		pb.add(name, CC.xy(1, 3));
		pb.add(recipes, CC.xyw(3, 3, 3));

		JScrollPane sp = new JScrollPane(cookbooks);
		sp.setPreferredSize(new Dimension(155, 205));
		pb.add(sp, CC.xyw(1, 5, 5));

		this.setContentPane(pb.build());
	}

	@Override
	protected void reset() {
		name.setText(""); // $NON-NLS-1$
		name.setBackground(NORMAL_BACKGROUND_TEXT_COLOR);

	}

	@Override
	protected void reload() {
		reset();

		Collection<Recipe> rs = Model.findAll(Recipe.class);
		DefaultComboBoxModel<Recipe> r = new DefaultComboBoxModel<Recipe>();
		for (Recipe recipe : rs) {
			r.addElement(recipe);
		}

		recipes.setModel(r);
	}

}
