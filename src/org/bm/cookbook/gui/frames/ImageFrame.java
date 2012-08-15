package org.bm.cookbook.gui.frames;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import org.bm.cookbook.db.model.Image;
import org.bm.cookbook.db.model.Model;
import org.bm.cookbook.gui.Messages;
import org.bm.cookbook.gui.frames.renderers.ImageListCellRenderer;
import org.bm.cookbook.gui.utils.GuiError;
import org.bm.cookbook.gui.utils.GuiErrorList;
import org.bm.cookbook.gui.utils.ImagePanel;
import org.bm.cookbook.gui.utils.ImagePreviewPanel;
import org.bm.cookbook.utils.CBUtils;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.prompt.PromptSupport;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.CC;
import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings("serial")
public class ImageFrame extends CookbookInternalFrame {
	private Image current;
	private JButton add;
	private JButton delete;
	private JButton update;

	private JTextField path;
	private JTextField name;
	private JButton search;

	private ImagePanel imagePanel;

	private JList<Image> imageList;

	public ImageFrame() {}

	@Override
	protected void createGui() {
		this.setTitle(Messages.getString("CookbookFrame.itemImage")); //$NON-NLS-1$
		FormLayout layout = new FormLayout("p, 4dlu, p, 4dlu, p", "p, 2dlu, p, 2dlu, p, 2dlu, f:p:g"); //$NON-NLS-1$ //$NON-NLS-2$

		add = new JButton(Messages.getString("ImageFrame.buttonAdd")); //$NON-NLS-1$

		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiErrorList l = new GuiErrorList(ImageFrame.this);
				if (name.getText().isEmpty()) {
					l.add(new GuiError(name, Messages.getString("errors.nameNotEmpty")));
				} else {
					Image i = Image.findByName(name.getText());
					if (null != i) {
						l.add(new GuiError(null, Messages.getString("errors.imageAlreadyExists")));
					}
				}

				if (path.getText().isEmpty()) {
					l.add(new GuiError(path, Messages.getString("errors.imagePathNotEmpty")));
				}

				if (l.showErrors()) {
					return;
				}

				Image i = new Image();
				i.setName(name.getText());
				i.setImagePath(path.getText());
				i.save();
				MainFrame.updateStatus(Messages.getString("Frame.statusTextDataDeleted")); //$NON-NLS-1$
				reload();
			}
		});
		delete = new JButton(Messages.getString("ImageFrame.buttonDelete")); //$NON-NLS-1$
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (current != null) {
					current.remove();
					current = null;
					MainFrame.updateStatus(Messages.getString("Frame.statusTextDataSaved")); //$NON-NLS-1$
					reload();
				}
			}
		});
		update = new JButton(Messages.getString("ImageFrame.buttonUpdate")); //$NON-NLS-1$
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (null != current) {
					Model.getEm().getTransaction().begin();
					current.setName(name.getText());
					current.setImagePath(path.getText());
					Model.getEm().getTransaction().commit();
					MainFrame.updateStatus(Messages.getString("Frame.statusTextDataUpdated")); //$NON-NLS-1$
					reload();
				}
			}
		});

		name = new JTextField();
		PromptSupport.setPrompt(Messages.getString("ImageFrame.nameGhostText"), name); //$NON-NLS-1$

		path = new JTextField();
		path.setEditable(false);
		PromptSupport.setPrompt(Messages.getString("ImageFrame.pathGhostText"), path); //$NON-NLS-1$

		search = new JButton(Messages.getString("ImageFrame.buttonSearch")); //$NON-NLS-1$
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(new File(".")); //$NON-NLS-1$

				jfc.setFileFilter(new FileFilter() {

					@Override
					public String getDescription() {
						return Messages.getString("ImageFrame.imageFileDescription"); //$NON-NLS-1$
					}

					@Override
					public boolean accept(File f) {
						String ext = CBUtils.getExtension(f);
						boolean isExtension = CBUtils.isImageExtension(ext);
						return isExtension || f.isDirectory();
					}
				});
				ImagePreviewPanel preview = new ImagePreviewPanel();
				jfc.setAccessory(preview);
				jfc.addPropertyChangeListener(preview);

				if (JFileChooser.APPROVE_OPTION == jfc.showOpenDialog(ImageFrame.this)) {
					File selectedFile = jfc.getSelectedFile();
					path.setText(selectedFile.getAbsolutePath());
					try {
						BufferedImage i = ImageIO.read(selectedFile);

						imagePanel.setImage(i);
					} catch (IOException e1) {
						JXErrorPane.showDialog(e1);
					}
				}
			}
		});
		imagePanel = new ImagePanel();

		imageList = new JList<Image>();

		imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		imageList.setPreferredSize(new Dimension(150, 200));
		imageList.setSize(new Dimension(150, 200));
		imageList.setCellRenderer(new ImageListCellRenderer(48));

		imageList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {

				current = imageList.getSelectedValue();
				if (null != current) {
					name.setText(current.getName());
					path.setText(current.getImagePath());
					BufferedImage i = current.getBufferedImage();
					imagePanel.setImage(i);
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

		pb.add(path, CC.xyw(1, 3, 5));
		pb.add(name, CC.xyw(1, 5, 3));
		pb.add(search, CC.xy(5, 5));

		pb.add(imagePanel, CC.xy(1, 7));
		JScrollPane sp = new JScrollPane(imageList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.setPreferredSize(new Dimension(155, 205));
		pb.add(sp, CC.xyw(3, 7, 3));

		this.setContentPane(pb.build());
	}

	@Override
	protected void reload() {
		reset();

		DefaultListModel<Image> m = new DefaultListModel<>();

		Collection<Image> images = Image.findAll(Image.class);
		for (Image image : images) {
			m.addElement(image);
		}

		imageList.setModel(m);

	}

	@Override
	protected void reset() {
		imagePanel.setImage(null);
		name.setBackground(NORMAL_BACKGROUND_TEXT_COLOR);
		name.setText(""); //$NON-NLS-1$
		path.setBackground(NORMAL_BACKGROUND_TEXT_COLOR);
		path.setText(""); //$NON-NLS-1$
	}
}
