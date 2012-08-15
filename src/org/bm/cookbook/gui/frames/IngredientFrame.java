package org.bm.cookbook.gui.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.bm.cookbook.db.model.Image;
import org.bm.cookbook.db.model.Ingredient;
import org.bm.cookbook.db.model.Model;
import org.bm.cookbook.gui.Messages;
import org.bm.cookbook.gui.frames.renderers.HasImageListCellRenderer;
import org.bm.cookbook.gui.frames.renderers.ImageListCellRenderer;
import org.bm.cookbook.gui.utils.GuiError;
import org.bm.cookbook.gui.utils.GuiErrorList;
import org.jdesktop.swingx.prompt.PromptSupport;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings("serial")
public class IngredientFrame extends CookbookInternalFrame {
	private JButton add;
	private JButton delete;
	private JButton update;

	private Ingredient current;
	private JTextField name;
	private JList<Ingredient> ingredientsList;

	private JComboBox<Image> imageComboBox;

	public IngredientFrame() {
		current = null;
	}

	@Override
	protected void createGui() {
		this.setTitle(Messages.getString("CookbookFrame.itemIngredient"));

		{
			FormLayout layout = new FormLayout("p:g, 4dlu, p:g, 4dlu, p:g", "p, 2dlu, f:p, 2dlu, f:p:g");

			add = new JButton(Messages.getString("UnitFrame.buttonAdd")); //$NON-NLS-1$
			add.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GuiErrorList l = new GuiErrorList(IngredientFrame.this);

					if (name.getText().isEmpty()) {
						l.add(new GuiError(name, Messages.getString("errors.nameNotEmpty")));
					} else {
						Ingredient u = Ingredient.findByName(name.getText());
						if (null != u) {
							l.add(new GuiError(null, Messages.getString("errors.ingredientAlreadyExists")));
						}
					}

					if (imageComboBox.getSelectedIndex() == -1) {
						l.add(new GuiError(imageComboBox, Messages.getString("errors.imageNotEmpty")));
					}

					if (l.showErrors()) {
						return;
					}

					Ingredient i = new Ingredient();
					i.setName(name.getText());
					if (Image.nullImage.equals(imageComboBox.getSelectedItem())) {
						i.setImage(null);
					} else {
						i.setImage((Image) imageComboBox.getSelectedItem());
					}
					i.save();
					MainFrame.updateStatus(Messages.getString("Frame.statusTextDataSaved")); //$NON-NLS-1$
					reload();
				}

			});
			delete = new JButton(Messages.getString("UnitFrame.buttonDelete")); //$NON-NLS-1$
			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (current != null) {
						current.remove();
						current = null;
						MainFrame.updateStatus(Messages.getString("Frame.statusTextDataDeleted")); //$NON-NLS-1$
						reload();
					}
				}
			});

			update = new JButton(Messages.getString("UnitFrame.buttonUpdate")); //$NON-NLS-1$

			update.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (null != current) {
						current.beginTransaction();
						current.setName(name.getText());
						if (Image.nullImage.equals(imageComboBox.getSelectedItem())) {
							current.setImage(null);
						} else {
							current.setImage((Image) imageComboBox.getSelectedItem());
						}
						current.commitTransaction();
						MainFrame.updateStatus(Messages.getString("Frame.statusTextDataUpdated")); //$NON-NLS-1$
						reload();
					}
				}
			});

			name = new JTextField();
			PromptSupport.setPrompt(Messages.getString("Frame.ghostTextEnterName"), name); //$NON-NLS-1$

			imageComboBox = new JComboBox<>();

			imageComboBox.setRenderer(new ImageListCellRenderer(30));

			ingredientsList = new JList<Ingredient>();
			ingredientsList.setCellRenderer(new HasImageListCellRenderer(30));
			ingredientsList.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					current = ingredientsList.getSelectedValue();
					if (null != current) {
						name.setText(current.getName());
						if (current.getImage() == null) {
							imageComboBox.setSelectedItem(Image.nullImage);
						} else {
							imageComboBox.setSelectedItem(current.getImage());
						}
					} else {
						reset();
					}
				}
			});

			PanelBuilder pb = new PanelBuilder(layout);
			pb.border(Borders.DIALOG);

			pb.add(add, CC.xy(1, 1));
			pb.add(delete, CC.xy(3, 1));
			pb.add(update, CC.xy(5, 1));

			pb.add(name, CC.xyw(1, 3, 3));
			pb.add(imageComboBox, CC.xy(5, 3));

			JScrollPane sp = new JScrollPane(ingredientsList);
			sp.setPreferredSize(new Dimension(155, 205));
			pb.add(sp, CC.xyw(1, 5, 5));

			this.setContentPane(pb.build());
		}
	}

	@Override
	protected void reload() {
		reset();

		DefaultComboBoxModel<Image> cm = new DefaultComboBoxModel<>();
		Collection<Image> images = Model.findAll(Image.class);
		cm.addElement(Image.nullImage);
		for (Image image : images) {
			cm.addElement(image);
		}

		imageComboBox.setModel(cm);

		DefaultListModel<Ingredient> m = new DefaultListModel<>();

		Collection<Ingredient> ingredient = Model.findAll(Ingredient.class);
		for (Ingredient unit : ingredient) {
			m.addElement(unit);
		}

		ingredientsList.setModel(m);
		
	}

	@Override
	protected void reset() {
		name.setText(""); // $NON-NLS-1$
		name.setBackground(NORMAL_BACKGROUND_TEXT_COLOR);
	}

}
