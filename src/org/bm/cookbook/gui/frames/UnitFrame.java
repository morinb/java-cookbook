package org.bm.cookbook.gui.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.bm.cookbook.db.model.Model;
import org.bm.cookbook.db.model.Unit;
import org.bm.cookbook.gui.Messages;
import org.bm.cookbook.gui.utils.GuiError;
import org.bm.cookbook.gui.utils.GuiErrorList;
import org.jdesktop.swingx.prompt.PromptSupport;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings("serial")
public class UnitFrame extends CookbookInternalFrame {
	private Unit current;
	private JTextField name;
	private JTextField abbreviation;

	private JButton add;
	private JButton delete;
	private JButton update;

	private JList<Unit> unitsList;

	public UnitFrame() {
		current = null;
	}

	@Override
	protected void createGui() {
		this.setTitle(Messages.getString("CookbookFrame.itemUnit")); //$NON-NLS-1$

		{

			add = new JButton(Messages.getString("UnitFrame.buttonAdd")); //$NON-NLS-1$
			add.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					GuiErrorList l = new GuiErrorList(UnitFrame.this);

					if (name.getText().isEmpty()) {
						l.add(new GuiError(name, Messages.getString("errors.nameNotEmpty")));
					} else {
						Unit u = Unit.findByName(name.getText());
						if (null != u) {
							l.add(new GuiError(null, Messages.getString("errors.unitAlreadyExists")));
						}
					}

					if (abbreviation.getText().isEmpty()) {
						l.add(new GuiError(abbreviation, Messages.getString("errors.abbreviationNotEmpty")));
					}

					if (l.showErrors()) {
						return;
					}

					Unit u = new Unit();
					u.setName(name.getText());
					u.setAbbreviation(abbreviation.getText());
					u.save();
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
						Model.getEm().getTransaction().begin();
						current.setName(name.getText());
						current.setAbbreviation(abbreviation.getText());
						Model.getEm().getTransaction().commit();
						MainFrame.updateStatus(Messages.getString("Frame.statusTextDataUpdated")); //$NON-NLS-1$
						reload();
					}
				}
			});

			name = new JTextField();
			PromptSupport.setPrompt(Messages.getString("Frame.ghostTextEnterName"), name); //$NON-NLS-1$
			abbreviation = new JTextField();
			PromptSupport.setPrompt(Messages.getString("UnitFrame.ghostTextEnterAbbreviation"), abbreviation); //$NON-NLS-1$
			unitsList = new JList<Unit>();

			unitsList.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					current = unitsList.getSelectedValue();
					if (null != current) {
						name.setText(current.getName());
						abbreviation.setText(current.getAbbreviation());
					} else {
						reset();
					}
				}
			});
			FormLayout layout = new FormLayout("p:g, 4dlu, p:g, 4dlu, p:g", "p, 2dlu, p, 2dlu, f:p:g"); //$NON-NLS-1$ //$NON-NLS-2$

			PanelBuilder pb = new PanelBuilder(layout);
			pb.border(Borders.DIALOG);

			pb.add(add, CC.xy(1, 1));
			pb.add(delete, CC.xy(3, 1));
			pb.add(update, CC.xy(5, 1));

			pb.add(name, CC.xyw(1, 3, 3));
			pb.add(abbreviation, CC.xy(5, 3));

			JScrollPane sp = new JScrollPane(unitsList);
			sp.setPreferredSize(new Dimension(155, 205));
			pb.add(sp, CC.xyw(1, 5, 5));

			this.setContentPane(pb.build());
		}

	}

	@Override
	protected void reload() {
		reset();

		DefaultListModel<Unit> m = new DefaultListModel<>();

		Collection<Unit> units = Model.findAll(Unit.class);
		for (Unit unit : units) {
			m.addElement(unit);
		}

		unitsList.setModel(m);

	}

	@Override
	protected void reset() {
		name.setBackground(NORMAL_BACKGROUND_TEXT_COLOR);
		abbreviation.setBackground(NORMAL_BACKGROUND_TEXT_COLOR);
		name.setText(""); //$NON-NLS-1$
		abbreviation.setText(""); //$NON-NLS-1$
	}
}
