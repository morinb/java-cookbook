package org.bm.cookbook.gui.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.bm.cookbook.db.model.Image;
import org.bm.cookbook.db.model.Ingredient;
import org.bm.cookbook.db.model.Model;
import org.bm.cookbook.db.model.Recipe;
import org.bm.cookbook.db.model.RecipeIngredient;
import org.bm.cookbook.db.model.Step;
import org.bm.cookbook.db.model.Unit;
import org.bm.cookbook.gui.Messages;
import org.bm.cookbook.gui.frames.renderers.HasImageListCellRenderer;
import org.bm.cookbook.gui.frames.renderers.ImageListCellRenderer;
import org.bm.cookbook.gui.utils.GuiError;
import org.bm.cookbook.gui.utils.GuiErrorList;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.prompt.PromptSupport;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings("serial")
public class RecipeFrame extends CookbookInternalFrame {
	private int currentRecipeIndex;
	private RecipeIngredient currentRecipeIngredient;

	private JButton addRecipe;
	private JButton deleteRecipe;
	private JButton updateRecipe;

	private JButton addRecipeIngredient;
	private JButton deleteRecipeIngredient;
	private JButton updateRecipeIngredient;
	private JSpinner quantity;
	private JComboBox<Unit> units;
	private JComboBox<Ingredient> ingredients;
	private JScrollPane scrollPaneRecipeIngredients;
	private JList<RecipeIngredient> recipeIngredients;

	private JScrollPane scrollPaneStep;
	private JTextArea stepText;

	private JTextField name;
	private JComboBox<Image> images;

	private JScrollPane scrollPaneRecipes;
	private JList<Recipe> recipes;

	private JCheckBox preheat;

	public RecipeFrame() {}

	@Override
	protected void createGui() {
		currentRecipeIndex = -1;
		this.setTitle(Messages.getString("CookbookFrame.itemRecipe"));
		addRecipe = new JXButton(Messages.getString("UnitFrame.buttonAdd")); //$NON-NLS-1$
		addRecipe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiErrorList l = new GuiErrorList(RecipeFrame.this);
				if (name.getText().isEmpty()) {
					l.add(new GuiError(name, Messages.getString("errors.nameNotEmpty")));
				}

				if (l.showErrors()) {
					return;
				}

				Recipe r = new Recipe();
				r.setTitle(name.getText());

				if (Image.nullImage.equals((Image) images.getSelectedItem())) {
					r.setImage(null);
				} else {
					r.setImage((Image) images.getSelectedItem());
				}
				r.setPreheat(preheat.isSelected());

				r.getSteps().clear();
				Step s = new Step();
				s.setText(stepText.getText());
				r.getSteps().add(s);

				r.getRecipeIngredients().clear();
				Collection<RecipeIngredient> ris = new ArrayList<>();
				for (int i = 0; i < recipeIngredients.getModel().getSize(); i++) {
					RecipeIngredient element = recipeIngredients.getModel().getElementAt(i);
					ris.add(element);
				}

				r.getRecipeIngredients().addAll(ris);

				r.save();
				reload();

			}
		});
		deleteRecipe = new JButton(Messages.getString("UnitFrame.buttonDelete")); //$NON-NLS-1$
		deleteRecipe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentRecipeIndex != -1) {
					Recipe currentRecipe = recipes.getModel().getElementAt(currentRecipeIndex);
					if (currentRecipe != null) {
						currentRecipe.setImage(null);
						// for (RecipeIngredient ri :
						// currentRecipe.getRecipeIngredients()) {
						// ri.remove();
						// }
						currentRecipe.remove();

						reload();
					}
				}
			}
		});
		updateRecipe = new JButton(Messages.getString("UnitFrame.buttonUpdate")); //$NON-NLS-1$
		updateRecipe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentRecipeIndex != -1) {
					Recipe currentRecipe = recipes.getModel().getElementAt(currentRecipeIndex);
					if (currentRecipe != null) {
						currentRecipe.beginTransaction();
						currentRecipe.setTitle(name.getText());
						currentRecipe.setImage((Image) images.getSelectedItem());

						currentRecipe.setPreheat(preheat.isSelected());

						currentRecipe.getSteps().clear();
						Step s = new Step();
						s.setText(stepText.getText());
						currentRecipe.getSteps().add(s);

						currentRecipe.getRecipeIngredients().clear();
						Collection<RecipeIngredient> ris = new ArrayList<>();
						for (int i = 0; i < recipeIngredients.getModel().getSize(); i++) {
							RecipeIngredient element = recipeIngredients.getModel().getElementAt(i);
							ris.add(element);
						}

						currentRecipe.getRecipeIngredients().addAll(ris);

						currentRecipe.commitTransaction();
						reload();
						recipes.setSelectedValue(currentRecipe, true);
						if (currentRecipe.getImage() == null) {
							images.setSelectedItem(Image.nullImage);
						} else {
							images.setSelectedItem(currentRecipe.getImage());
						}
					}
				}
			}
		});

		addRecipeIngredient = new JButton(Messages.getString("UnitFrame.buttonAdd")); //$NON-NLS-1$
		addRecipeIngredient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentRecipeIndex != -1) {
					Recipe currentRecipe = recipes.getModel().getElementAt(currentRecipeIndex);
					if (currentRecipe == null) {
						GuiErrorList l = new GuiErrorList(RecipeFrame.this);

						l.add(new GuiError(name, Messages.getString("RecipeFrame.createRecipeBefore")));

						addErrorCircle(addRecipe);
						l.showErrors();

						return;
					}

					RecipeIngredient ri = new RecipeIngredient();

					ri.setQuantity((Integer) quantity.getValue());
					ri.setIngredient((Ingredient) ingredients.getSelectedItem());
					ri.setUnit((Unit) units.getSelectedItem());

					ri.setRecipe(currentRecipe);
					ri.save();
					reloadRecipeIngredients();
				}
			}
		});
		deleteRecipeIngredient = new JButton(Messages.getString("UnitFrame.buttonDelete")); //$NON-NLS-1$
		deleteRecipeIngredient.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentRecipeIngredient != null) {
					currentRecipeIngredient.remove();
					currentRecipeIngredient = null;
					MainFrame.updateStatus(Messages.getString("Frame.statusTextDataDeleted")); //$NON-NLS-1$
					reloadRecipeIngredients();
				}
			}
		});
		updateRecipeIngredient = new JButton(Messages.getString("UnitFrame.buttonUpdate")); //$NON-NLS-1$
		updateRecipeIngredient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (currentRecipeIngredient != null) {
					currentRecipeIngredient.beginTransaction();
					currentRecipeIngredient.setQuantity((Integer) quantity.getValue());
					currentRecipeIngredient.setUnit((Unit) units.getSelectedItem());
					currentRecipeIngredient.setIngredient((Ingredient) ingredients.getSelectedItem());
					currentRecipeIngredient.commitTransaction();
					MainFrame.updateStatus(Messages.getString("Frame.statusTextDataUpdated")); //$NON-NLS-1$
					reloadRecipeIngredients();
				}
			}
		});
		SpinnerNumberModel snm = new SpinnerNumberModel(1, 0, 100000, 1);
		quantity = new JSpinner(snm);
		units = new JComboBox<Unit>();
		units.setPreferredSize(new Dimension(150, units.getPreferredSize().height));
		ingredients = new JComboBox<Ingredient>();
		ingredients.setRenderer(new HasImageListCellRenderer(30));
		ingredients.setPreferredSize(new Dimension(150, ingredients.getPreferredSize().height));
		recipeIngredients = new JList<RecipeIngredient>(new DefaultListModel<RecipeIngredient>());
		recipeIngredients.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				currentRecipeIngredient = recipeIngredients.getSelectedValue();
				if (currentRecipeIngredient != null) {
					quantity.setValue(currentRecipeIngredient.getQuantity());
					units.setSelectedItem(currentRecipeIngredient.getUnit());
					ingredients.setSelectedItem(currentRecipeIngredient.getIngredient());
				} else {
					reset();
				}
			}
		});

		stepText = new JTextArea();

		PromptSupport.setPrompt(Messages.getString("Frame.ghostTextEnterRecipeStep"), stepText);
		stepText.setRows(10);

		name = new JTextField();
		PromptSupport.setPrompt(Messages.getString("Frame.ghostTextEnterName"), name);
		images = new JComboBox<Image>();
		images.setRenderer(new ImageListCellRenderer(30));
		images.setPreferredSize(new Dimension(150, images.getPreferredSize().height));
		recipes = new JList<Recipe>(new DefaultListModel<Recipe>());
		recipes.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (currentRecipeIndex != -1) {
					Recipe currentRecipe = recipes.getModel().getElementAt(currentRecipeIndex);
					currentRecipe = (Recipe) recipes.getSelectedValue();
					if (null != currentRecipe) {
						name.setText(currentRecipe.getTitle());
						if (null == currentRecipe.getImage()) {
							images.setSelectedItem(Image.nullImage);
						} else {
							images.setSelectedItem(currentRecipe.getImage());
						}
						reloadRecipeIngredients();
					}
				}
			}
		});

		preheat = new JCheckBox(Messages.getString("RecipeFrame.preheatOven"));

		FormLayout layout = new FormLayout("p:g, 4dlu, p:g, 4dlu, p:g, 4dlu, p:g, 4dlu, p:g",
				"t:p, 2dlu, p, 2dlu, p, 2dlu, p, 4dlu, f:p:g, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p");

		layout.setColumnGroups(new int[][] { { 1, 3, 7 } });
		PanelBuilder pb = new PanelBuilder(layout);
		pb.border(Borders.DIALOG);

		pb.add(quantity, CC.xy(1, 1));
		pb.add(units, CC.xyw(3, 1, 3));
		pb.add(ingredients, CC.xyw(7, 1, 3));

		scrollPaneRecipeIngredients = new JScrollPane(recipeIngredients);
		pb.add(scrollPaneRecipeIngredients, CC.xywh(1, 3, 7, 5));
		pb.add(addRecipeIngredient, CC.xy(9, 3));
		pb.add(deleteRecipeIngredient, CC.xy(9, 5));
		pb.add(updateRecipeIngredient, CC.xy(9, 7));

		scrollPaneStep = new JScrollPane(stepText);
		pb.add(scrollPaneStep, CC.xyw(1, 9, 9));

		pb.add(preheat, CC.xy(1, 11));
		pb.add(name, CC.xyw(3, 11, 3));
		pb.add(images, CC.xyw(7, 11, 3));

		scrollPaneRecipes = new JScrollPane(recipes);
		pb.add(scrollPaneRecipes, CC.xywh(1, 13, 7, 5));
		pb.add(addRecipe, CC.xy(9, 13));
		pb.add(deleteRecipe, CC.xy(9, 15));
		pb.add(updateRecipe, CC.xy(9, 17));

		this.setContentPane(pb.build());
	}

	@Override
	protected void reset() {
		name.setText(""); // $NON-NLS-1$
		name.setBackground(NORMAL_BACKGROUND_TEXT_COLOR);
		addRecipe.setBackground(NORMAL_BACKGROUND_BUTTON_COLOR);
		removeAllCircles();
	}

	@Override
	protected void reload() {
		reset();

		reloadRecipeIngredients();

		reloadRecipe();

	}

	private void reloadRecipe() {
		DefaultComboBoxModel<Image> cm = new DefaultComboBoxModel<>();
		Collection<Image> im = Model.findAll(Image.class);
		cm.addElement(Image.nullImage);
		for (Image image : im) {
			cm.addElement(image);
		}

		this.images.setModel(cm);
		DefaultListModel<Recipe> rsm = (DefaultListModel<Recipe>) recipes.getModel();
		rsm.clear();
		Collection<Recipe> rs = Model.findAll(Recipe.class);
		for (Recipe recipe : rs) {
			rsm.addElement(recipe);
		}
		recipes.setModel(rsm);
		recipes.setSelectedIndex(currentRecipeIndex);
	}

	private void reloadRecipeIngredients() {
		DefaultComboBoxModel<Ingredient> m = new DefaultComboBoxModel<>();

		Collection<Ingredient> ings = Model.findAll(Ingredient.class);
		for (Ingredient ig : ings) {
			m.addElement(ig);
		}

		ingredients.setModel(m);

		DefaultComboBoxModel<Unit> um = new DefaultComboBoxModel<Unit>();
		Collection<Unit> us = Model.findAll(Unit.class);
		for (Unit unit : us) {
			um.addElement(unit);
		}

		units.setModel(um);

		if (currentRecipeIndex != -1) {
			Recipe currentRecipe = recipes.getModel().getElementAt(currentRecipeIndex);
			DefaultListModel<RecipeIngredient> rim = new DefaultListModel<RecipeIngredient>();
			Collection<RecipeIngredient> ri = RecipeIngredient.findRecipeIngredientByRecipe(currentRecipe.getOid());
			for (RecipeIngredient recipeIngredient : ri) {
				rim.addElement(recipeIngredient);
			}
			recipeIngredients.setModel(rim);
		}
	}
}
