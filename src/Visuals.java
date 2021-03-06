//Comments from keshav

//Selected Archive buttons shouldnt be able to have an action on it
//When you move back to a screen clear all previous actions, uncheck boxes
//Select Archive screen needs back button
//New Archive screen information to type in should be black color not the gray filler info
//Increase size of file explorer thing yes sir yes sir

//Comments from big hammy
//Automatic trim popup not functioning properly 

//-arch list should initially be empty(to be done during integration)

// New Comments
// Select Data location and select Archive Location not working on archive page
// Select archive location popup in Save As

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.Enumeration;

public class Visual implements ActionListener {
	enum Page {
		START, ARCHIVE_LIST, NEW_ARCHIVE, OPEN_FILE, OLD_ARCHIVE, MODIFICATION_REPORT, SETTINGS, SAVE, TRIM, BACKUP, RECOVERY, RESTORE_LOC;
	}

	private static JFrame frame;
	private static JPanel start;
	private static JPanel archiveList;
	private static JPanel newArchive;
	private static JPanel openFile;
	private static JPanel oldArchive;
	private static JPanel confirmation;
	private static JPanel settings;
	private static JPanel restoreLoc;
	private static JPanel restore;
	private static JPanel trim;
	private static JPanel trimNum;
	private static JPanel autoConfirmation;
	private static JPanel backUp;
	private static JPanel recovery;
	private static Color w;
	private static Color b;
	private static Color g;
	private static Page currPage;
	private static boolean valid;
	private static boolean validSave = false;;
	private static JTextField trimBehavior;
	private static String archive;
	private static int selectedArchiveIndex;
	private static int stateIndex;
	private String restoreLocation;
	private ProgressBar bar;
	private JFrame popup;
	final Timer t = new Timer(10, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			bar.repaint();
			popup.repaint();
		}
	});

	private Mainframe mainframe;
	private ButtonGroup group;

	public static String browseFileExplorer() {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.showOpenDialog(null);
		if (jfc.getSelectedFile() != null)
			return jfc.getSelectedFile().toString();
		else
			return null;
	}

	public Visual() {
		start = new JPanel();
		archiveList = new JPanel();
		newArchive = new JPanel();
		openFile = new JPanel();
		oldArchive = new JPanel();
		new JPanel();
		confirmation = new JPanel();
		settings = new JPanel();
		restoreLoc = new JPanel();
		restore = new JPanel();
		trim = new JPanel();
		trimNum = new JPanel();
		autoConfirmation = new JPanel();
		backUp = new JPanel();
		recovery = new JPanel();
		w = new Color(250, 250, 250);
		b = new Color(100, 150, 200);
		g = new Color(180, 180, 180);

		currPage = Page.START;
		valid = false;
		trimBehavior = new JTextField("1");
		archive = "[Old Archive Name]";
		mainframe = new Mainframe();
		makeStart();
		makeArchiveList();
		makeNewArchive();
		makeOpenFile();
	}

	public void makeStart() {
		start.setBackground(w);
		start.setLayout(new BoxLayout(start, BoxLayout.Y_AXIS));
		start.setBorder(BorderFactory.createEmptyBorder(100, 10, 100, 10));
		JLabel title = new JLabel("Welcome to Sync-Tool");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
		title.setForeground(b);
		JButton startButton = new JButton();
		startButton.setText("   Start   ");
		startButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		startButton.setBackground(b);
		startButton.setForeground(w);
		start.add(Box.createVerticalGlue());
		start.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		start.add(Box.createRigidArea(new Dimension(0, 100)));
		start.add(startButton);
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		start.add(Box.createVerticalGlue());

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currPage = Page.ARCHIVE_LIST;
				frame.setContentPane(archiveList);
				frame.revalidate();
			}
		});
	}

	public void makeArchiveList() {
		archiveList = new JPanel();
		currPage = Page.ARCHIVE_LIST;
		archiveList.setBackground(w);
		archiveList.setLayout(new BoxLayout(archiveList, BoxLayout.Y_AXIS));
		archiveList.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));
		JLabel title = new JLabel("Sync-Tool");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JPanel grid = new JPanel();
		grid.setLayout(new GridLayout(2, 1, 0, 0));
		JLabel scrollHeader = new JLabel(
				"                                       Archives" + "                                       ");
		scrollHeader.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		scrollHeader.setOpaque(true);
		scrollHeader.setForeground(w);
		scrollHeader.setBackground(b);
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(0, 1, 0, 0));
		container.setBackground(w);
		if (mainframe.getArchives().size() == 0) {
			JLabel temp = new JLabel();
			temp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
			temp.setText("Add new archives.");
			temp.setBackground(w);
			temp.setForeground(g);
			temp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			temp.setHorizontalAlignment(JButton.LEFT);
			container.add(temp);

		} else {
			for (int i = 0; i < mainframe.getArchives().size(); i++) {
				JButton temp = new JButton();
				temp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
				temp.setText(mainframe.getArchive(i).getName());
				temp.setBackground(w);
				temp.setForeground(b);
				temp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						selectedArchiveIndex = mainframe.findIndexOfArchive(temp.getText());
						oldArchive.removeAll();
						archive = e.getActionCommand();
						makeOldArchive();
						currPage = Page.OLD_ARCHIVE;
						frame.setContentPane(oldArchive);
						frame.revalidate();
					}
				});
				temp.setHorizontalAlignment(JButton.LEFT);
				container.add(temp);
			}
		}
		JScrollPane scroll = new JScrollPane(container);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBackground(w);
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		JButton newArchiveButton = new JButton();
		newArchiveButton.setText(" New Archive ");
		newArchiveButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		newArchiveButton.setBackground(b);
		newArchiveButton.setForeground(w);
		JButton findArchive = new JButton();
		findArchive.setText(" Find Archive ");
		findArchive.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		findArchive.setBackground(b);
		findArchive.setForeground(w);
		JPanel p = new JPanel();
		p.setBackground(w);
		p.setLayout(new FlowLayout());
		p.add(findArchive);
		p.add(Box.createRigidArea(new Dimension(200, 0)));
		p.add(newArchiveButton);
		archiveList.add(backButton());
		archiveList.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
		archiveList.add(scrollHeader);
		scrollHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
		archiveList.add(scroll);
		scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 155, 100, 155));
		archiveList.add(p);
		newArchiveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		newArchiveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currPage = Page.NEW_ARCHIVE;
				frame.setContentPane(newArchive);
				frame.revalidate();
			}
		});
		findArchive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currPage = Page.OPEN_FILE;
				frame.setContentPane(openFile);
				frame.revalidate();
				if (mainframe.openArchive(browseFileExplorer(), "")) {
					selectedArchiveIndex = mainframe.getArchives().size() - 1;
					if (mainframe.getArchive(selectedArchiveIndex).check()) {
						makeRecovery();
						currPage = Page.RECOVERY;
						frame.setContentPane(recovery);
						frame.revalidate();
					} else {
						makeOldArchive();
						currPage = Page.OLD_ARCHIVE;
						frame.setContentPane(oldArchive);
						frame.revalidate();
					}
				} else {
					currPage = Page.ARCHIVE_LIST;
					frame.setContentPane(archiveList);
					frame.revalidate();
				}
			}
		});
	}

	public void makeNewArchive() {
		newArchive = new JPanel(new BorderLayout());
		newArchive.setBackground(w);
		newArchive.setLayout(new BoxLayout(newArchive, BoxLayout.Y_AXIS));
		newArchive.setBorder(BorderFactory.createEmptyBorder(0, 20, 70, 20));
		JLabel title = new JLabel("New Archive Page");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		final JTextField name = new HintText("Enter archive name");
		name.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		JPanel gridPane = new JPanel();
		gridPane.setLayout(new GridLayout(2, 2, 30, 30));
		gridPane.setBackground(w);
		final JTextField dLocation = new JTextField("Location of Current Data");
		dLocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		dLocation.setEditable(false);

		JButton selectDLocation = new JButton();
		selectDLocation.setText("Select Data Location");
		selectDLocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		selectDLocation.setBackground(b);
		selectDLocation.setForeground(w);
		final JTextField aLocation = new JTextField("Location Archive");
		aLocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		aLocation.setEditable(false);

		JButton selectALocation = new JButton();
		selectALocation.setText("Select Archive Location");
		selectALocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		selectALocation.setBackground(b);
		selectALocation.setForeground(w);

		gridPane.add(dLocation);
		gridPane.add(selectDLocation);
		gridPane.add(aLocation);
		gridPane.add(selectALocation);
		valid = !(name.getText().isEmpty() || dLocation.getText().isEmpty() || aLocation.getText().isEmpty());
		final JButton create = new JButton();
		create.setText("   Create New Archive   ");
		create.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		create.setBackground(g);
		create.setForeground(w);

		newArchive.add(backButton());
		newArchive.add(title, BorderLayout.NORTH);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(0, 100, 20, 100));
		newArchive.add(name);
		name.setAlignmentX(Component.CENTER_ALIGNMENT);
		name.setMaximumSize(new Dimension(900, 300));
		newArchive.add(gridPane);
		gridPane.setMaximumSize(new Dimension(2000, 800));
		gridPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		gridPane.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
		newArchive.add(create);
		create.setAlignmentX(Component.CENTER_ALIGNMENT);
		name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				valid = !name.getText().isEmpty() && !dLocation.getText().equals("Select Data Location")
						&& !aLocation.getText().equals("Location Archive");
				if (valid)
					create.setBackground(b);
			}
		});
		selectDLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dLocation.setText(browseFileExplorer());
				valid = !name.getText().isEmpty() && !dLocation.getText().equals("Select Data Location")
						&& !aLocation.getText().equals("Location Archive");
				if (valid)
					create.setBackground(b);
			}
		});
		selectALocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aLocation.setText(browseFileExplorer());
				valid = !(name.getText().isEmpty() || dLocation.getText().isEmpty() || aLocation.getText().isEmpty());
				if (valid)
					create.setBackground(b);
			}
		});
		create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (valid) {
					addArchive(name.getText(), dLocation.getText(), aLocation.getText() + "\\" + name.getText());
					selectedArchiveIndex = mainframe.getArchives().size() - 1;
					makeOldArchive();
					currPage = Page.OLD_ARCHIVE;
					frame.setContentPane(oldArchive);
					frame.revalidate();
				}
			}
		});
	}

	public void addArchive(String name, String dLoc, String aLoc) {
		mainframe.createArchive(dLoc, aLoc, name);
		archive = name;
		oldArchive.removeAll();
		makeOldArchive();
		newArchive.removeAll();
		makeNewArchive();
		archiveList.removeAll();
		makeArchiveList();
	}

	public void makeOpenFile() {
		openFile = new JPanel();
		currPage = Page.OPEN_FILE;
		openFile.setBackground(w);
		openFile.setLayout(new BoxLayout(openFile, BoxLayout.Y_AXIS));
		openFile.setBorder(BorderFactory.createEmptyBorder(0, 20, 70, 20));
		JLabel title = new JLabel("Open File Explorer");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		openFile.add(backButton());
		openFile.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

	public void makeOldArchive() {
		oldArchive = new JPanel();
		currPage = Page.OLD_ARCHIVE;
		oldArchive.setBackground(w);
		oldArchive.setLayout(new BoxLayout(oldArchive, BoxLayout.Y_AXIS));
		oldArchive.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		JButton backUpButton = new JButton();
		backUpButton.setText("Back Up");
		backUpButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		backUpButton.setBackground(b);
		backUpButton.setForeground(w);
		JButton settingsButton = new JButton();
		settingsButton.setText(" Settings ");
		settingsButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		settingsButton.setBackground(b);
		settingsButton.setForeground(w);
		final JButton trimButton = new JButton();
		trimButton.setText("   Trim   ");
		trimButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		trimBehavior.setVisible(false);
		if (!trimBehavior.isVisible())
			trimButton.setBackground(g);
		else
			trimButton.setBackground(b);
		trimButton.setForeground(w);
		JLabel title = new JLabel(mainframe.getArchive(selectedArchiveIndex).getName());
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JLabel scrollHeader = new JLabel("                     Drop Down with States" + "                     ");
		scrollHeader.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		scrollHeader.setOpaque(true);
		scrollHeader.setForeground(w);
		scrollHeader.setBackground(b);
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.setBackground(w);
		JScrollPane scroll = new JScrollPane(container);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBackground(w);
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		final JButton restore = new JButton();
		restore.setText("<html><center>Restore Data<br>to a Location </center></html>");
		restore.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		restore.setBackground(g);
		restore.setForeground(w);
		final JButton browse = new JButton();
		browse.setText("<html><center>Browse New<br>Location</center></html>");
		browse.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		browse.setBackground(g);
		browse.setForeground(w);
		final JButton getMod = new JButton();
		getMod.setText("<html><center>Get Modification<br>Report</center></html>");
		getMod.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		getMod.setBackground(g);
		getMod.setForeground(w);
		group = new ButtonGroup();
		valid = false;
		if (!((Integer) selectedArchiveIndex == null)) {
			ArrayList<String> statesList = mainframe.getArchive(selectedArchiveIndex).returnDisplay();
			for (int i = 0; i < statesList.size(); i++) {
				JRadioButton temp = new JRadioButton();
				temp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (valid == true && statesList.size() == 1) {
							valid = false;
							trimButton.setBackground(g);
							restore.setBackground(g);
							browse.setBackground(g);
							getMod.setBackground(g);
							group.clearSelection();
						} else {
							valid = true;
							trimButton.setVisible(true);
							trimButton.setBackground(b);
							restore.setBackground(b);
							browse.setBackground(b);
							getMod.setBackground(b);
						}
					}
				});
				temp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
				temp.setText(statesList.get(i));
				temp.setBackground(w);
				temp.setForeground(b);
				temp.setBorder(BorderFactory.createEmptyBorder());
				temp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
				temp.setName("" + i);
				group.add(temp);
				container.add(temp);
			}
		}
		oldArchive.add(backButton());
		oldArchive.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel p1 = new JPanel();
		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p1.setBackground(w);
		p1.setLayout(new FlowLayout());
		p1.add(backUpButton);
		p1.add(Box.createRigidArea(new Dimension(300, 0)));
		p1.add(settingsButton);
		p1.add(Box.createRigidArea(new Dimension(300, 0)));
		p1.add(trimButton);
		oldArchive.add(p1);
		oldArchive.add(scrollHeader);
		scrollHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		scrollHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
		oldArchive.add(scroll);
		scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 195, 50, 195));
		JPanel p2 = new JPanel();
		p2.setBackground(w);
		p2.setLayout(new FlowLayout());
		p2.add(restore);
		p2.add(Box.createRigidArea(new Dimension(100, 0)));
		p2.add(Box.createRigidArea(new Dimension(100, 0)));
		p2.add(getMod);
		oldArchive.add(p2);

		backUpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeBackUp();
				currPage = Page.BACKUP;
				frame.setContentPane(backUp);
				frame.revalidate();

			}
		});
		settingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeSettings();
				currPage = Page.SETTINGS;
				frame.setContentPane(settings);
				frame.revalidate();
			}
		});
		trimButton.addActionListener(this);
		restore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (valid) {
					makeRestoreLocation();
					currPage = Page.RESTORE_LOC;
					frame.setContentPane(restoreLoc);
					frame.revalidate();
				}
			}
		});

		getMod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (valid) {
					makeModificationReport();
				}
			}
		});

	}

	public void makeRestoreLocation() {
		int counter = 0;
		// We can only get button model from buttongroup so hard to identify
		// Therefore we extract all the button models from the buttongroup to find the
		// correct index;
		// Enumeration is an imported class very similar to an arraylist
		Enumeration<AbstractButton> buttonList = group.getElements();
		for (Enumeration<AbstractButton> buttons = buttonList; buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				stateIndex = counter;
			}
			counter++;
		}
		restoreLoc = new JPanel();
		restoreLoc.setBackground(w);
		restoreLoc.setLayout(new BoxLayout(restoreLoc, BoxLayout.Y_AXIS));
		restoreLoc.setBorder(BorderFactory.createEmptyBorder(0, 50, 70, 50));
		JLabel title = new JLabel("Restore");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2, 50, 10));
		p.setBackground(w);
		JButton selectALocation = new JButton();
		selectALocation.setText("Select Restore Location");
		selectALocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		selectALocation.setBackground(b);
		selectALocation.setForeground(w);
		final JTextField aLocation = new JTextField(mainframe.getArchive(selectedArchiveIndex).getDataPath());
		restoreLocation = mainframe.getArchive(selectedArchiveIndex).getDataPath();
		aLocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		aLocation.setEditable(false);
		p.add(aLocation);
		p.add(selectALocation);
		JButton restoreButton = new JButton();
		restoreButton.setText("Restore");
		restoreButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		restoreButton.setBackground(b);
		restoreButton.setForeground(w);
		restoreLoc.add(backButton());
		restoreLoc.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(0, 100, 50, 100));
		restoreLoc.add(p);
		restoreLoc.add(Box.createRigidArea(new Dimension(0, 100)));
		p.setAlignmentX(Component.CENTER_ALIGNMENT);
		p.setBorder(BorderFactory.createEmptyBorder(20, 100, 100, 100));
		p.setMaximumSize(new Dimension(Short.MAX_VALUE, 200));
		restoreLoc.add(restoreButton);
		restoreButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectALocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restoreLocation = browseFileExplorer();
				aLocation.setText(restoreLocation);
			}
		});
		aLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		restoreButton.addActionListener(this);
	}

	public void makeModificationReport() {
		final int selectedArchiveIndex = this.selectedArchiveIndex;
		JFrame window = new JFrame();
		window.setBackground(w);
		window.setVisible(true);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setLocationRelativeTo(null);
		JPanel modificationReport = new JPanel();
		modificationReport.setBackground(w);
		modificationReport.setLayout(new BoxLayout(modificationReport, BoxLayout.Y_AXIS));
		modificationReport.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
		JLabel title = new JLabel("Modification Report");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JLabel scrollHeader = new JLabel("                             Files in State                             ");
		scrollHeader.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		scrollHeader.setOpaque(true);
		scrollHeader.setForeground(w);
		scrollHeader.setBackground(b);
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(0, 1, 0, 0));
		container.setBackground(w);
		int index = 0;
		int counter = 0;
		// We can only get button model from buttongroup so hard to identify
		// Therefore we extract all the button models from the buttongroup to find the
		// correct index;
		// Enumeration is an imported class very similar to an arraylist
		Enumeration<AbstractButton> buttonList = group.getElements();
		for (Enumeration<AbstractButton> buttons = buttonList; buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				index = counter;
			}
			counter++;
		}
		final int stateIndex = index;
		for (int i = 0; i < mainframe.getArchive(selectedArchiveIndex).modificationReportDisplay(stateIndex)
				.size(); i++) {
			JLabel temp = new JLabel();
			temp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
			temp.setText(mainframe.getArchive(selectedArchiveIndex).modificationReportDisplay(stateIndex).get(i));
			temp.setBackground(w);
			temp.setForeground(b);
			temp.setBorder(BorderFactory.createEmptyBorder());
			temp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			temp.setHorizontalAlignment(JButton.LEFT);
			container.add(temp);
		}
		JScrollPane scroll = new JScrollPane(container);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBackground(w);
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		JButton callSave = new JButton();
		callSave.setText("Save Report");
		callSave.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		callSave.setBackground(b);
		callSave.setForeground(w);
		JButton print = new JButton();
		print.setText("Print");
		print.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		print.setBackground(b);
		print.setForeground(w);
		JButton dismiss = new JButton();
		dismiss.setText("Dismiss");
		dismiss.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		dismiss.setBackground(b);
		dismiss.setForeground(w);

		modificationReport.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
		modificationReport.add(scrollHeader);
		scrollHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
		modificationReport.add(scroll);
		scroll.setAlignmentX(Component.CENTER_ALIGNMENT);
		scroll.setBorder(BorderFactory.createEmptyBorder(0, 247, 100, 247));
		JPanel p1 = new JPanel();
		p1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		p1.setBackground(w);
		p1.setLayout(new FlowLayout());
		p1.add(callSave);
		p1.add(Box.createRigidArea(new Dimension(200, 0)));
		p1.add(print);
		p1.add(Box.createRigidArea(new Dimension(200, 0)));
		p1.add(dismiss);
		modificationReport.add(p1);
		window.setContentPane(modificationReport);
		window.revalidate();
		// Create return button
		JPanel returnButton = new JPanel();
		JButton back = new JButton();
		back.setText("  Back  ");
		back.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		back.setBackground(b);
		back.setForeground(w);
		returnButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 1300));
		returnButton.add(back);
		returnButton.setMaximumSize(new Dimension(Short.MAX_VALUE, 100));
		returnButton.setBackground(w);
		/// Now create the save as screen of the modifcatiion report
		JPanel saveAs = new JPanel();
		saveAs.setBackground(w);
		saveAs.setLayout(new BoxLayout(saveAs, BoxLayout.Y_AXIS));
		saveAs.setBorder(BorderFactory.createEmptyBorder(0, 50, 70, 50));
		JLabel saveAsTitle = new JLabel("Save As");
		saveAsTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		saveAsTitle.setForeground(b);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2, 50, 10));
		p.setBackground(w);
		JButton selectALocation = new JButton();
		selectALocation.setText("Select Save Location");
		selectALocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		selectALocation.setBackground(b);
		selectALocation.setForeground(w);
		final JTextField aLocation = new JTextField("Location of Save");
		aLocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		aLocation.setEditable(false);
		p.add(aLocation);
		p.add(selectALocation);
		JButton save = new JButton();
		save.setText("Save Report");
		save.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		save.setBackground(b);
		save.setForeground(w);
		saveAs.add(returnButton);
		saveAs.add(saveAsTitle);
		saveAsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		saveAsTitle.setBorder(BorderFactory.createEmptyBorder(0, 100, 50, 100));
		saveAs.add(p);
		saveAs.add(Box.createRigidArea(new Dimension(0, 100)));
		p.setAlignmentX(Component.CENTER_ALIGNMENT);
		p.setBorder(BorderFactory.createEmptyBorder(20, 100, 100, 100));
		p.setMaximumSize(new Dimension(Short.MAX_VALUE, 200));
		saveAs.add(save);
		save.setAlignmentX(Component.CENTER_ALIGNMENT);

		// actions on saveas
		selectALocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aLocation.setText(browseFileExplorer());
			}
		});
		aLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (mainframe.getArchive(selectedArchiveIndex).saveModification(stateIndex, aLocation.getText())) {
					window.setContentPane(modificationReport);
					window.revalidate();
				}
			}
		});
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.setContentPane(modificationReport);
				window.revalidate();
			}
		});

		// actions on modifcation report
		dismiss.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.dispose();
			}
		});
		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextPane jtp = new JTextPane();
				jtp.setBackground(Color.white);
				jtp.setText(mainframe.getArchive(selectedArchiveIndex).print(stateIndex));
				boolean show = true;
				try {
					jtp.print(null, null, show, null, null, show);
				} catch (java.awt.print.PrinterException ex) {
					ex.printStackTrace();
				}
			}
		});
		// Calls the save as screen
		callSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.setContentPane(saveAs);
				window.revalidate();
			}
		});

	}

	public void makeConfirmation() {
		confirmation = new JPanel();
		confirmation.setBackground(w);
		confirmation.setLayout(new BoxLayout(confirmation, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("Trim Confirmation");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		int counter = 0;
		// We can only get button model from buttongroup so hard to identify
		// Therefore we extract all the button models from the buttongroup to find the
		// correct index;
		// Enumeration is an imported class very similar to an arraylist
		Enumeration<AbstractButton> buttonList = group.getElements();
		for (Enumeration<AbstractButton> buttons = buttonList; buttons.hasMoreElements();) {
			AbstractButton button = buttons.nextElement();

			if (button.isSelected()) {
				stateIndex = counter;
			}
			counter++;
		}
		JLabel message = new JLabel("<html><center>" + "WARNING: You have selected to trim State "
				+ mainframe.getArchive(selectedArchiveIndex).states.get(stateIndex).getID()
				+ ". This and all previous backups will be permanently deleted. "
				+ "Proceed with the trim?</center></html>", SwingConstants.CENTER);
		message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		message.setForeground(b);
		confirmation.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(50, 20, 100, 20));
		confirmation.add(message);
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		message.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
		confirmation.setPreferredSize(new Dimension(1000, 700));
	}

	public void makeSettings() {
		validSave = false;
		settings = new JPanel();
		settings.setBackground(w);
		settings.setLayout(new BoxLayout(settings, BoxLayout.Y_AXIS));
		settings.setBorder(BorderFactory.createEmptyBorder(0, 50, 70, 50));
		JLabel title = new JLabel("Settings");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2, 50, 10));
		p.setBackground(w);
		JButton selectDLocation = new JButton();
		selectDLocation.setText("Select Data Location");
		selectDLocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		selectDLocation.setBackground(b);
		selectDLocation.setForeground(w);
		final JTextField dLocation = new JTextField("Location of Current Data");
		dLocation.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		dLocation.setEditable(false);
		p.add(selectDLocation);
		p.add(dLocation);
		JButton trimButton = new JButton();
		trimButton.setText("Trim Behavior");
		trimButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		trimButton.setBackground(b);
		trimButton.setForeground(w);
		JButton save = new JButton();
		save.setText("Confirm New Data Location");
		save.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		save.setBackground(g);
		save.setForeground(w);
		save.setAlignmentX(Component.CENTER_ALIGNMENT);
		settings.add(backButton());
		settings.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
		settings.add(p);
		p.setAlignmentX(Component.CENTER_ALIGNMENT);
		p.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		p.setMaximumSize(new Dimension(Short.MAX_VALUE, 100));
		trimButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel space = new JPanel();
		space.setBackground(w);
		space.add(Box.createHorizontalGlue());
		space.add(save);
		space.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 0));
		space.setAlignmentX(Component.CENTER_ALIGNMENT);

		settings.add(space);
		JPanel trimPanel = new JPanel();
		trimPanel.setBackground(w);
		trimPanel.add(Box.createHorizontalGlue());
		trimPanel.add(trimButton);
		trimPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 0));
		trimPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		settings.add(trimPanel);
		selectDLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String location = browseFileExplorer();
				if (!location.equals(null)) {
					dLocation.setText(location);
					save.setBackground(b);
					validSave = true;
				}
			}
		});
		trimButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currPage = Page.TRIM;
				makeTrim(false);
				frame.setContentPane(trim);
				frame.revalidate();
			}
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (validSave) {
					mainframe.getArchive(selectedArchiveIndex).changeDataLocation(dLocation.getText());
					currPage = Page.OLD_ARCHIVE;
					frame.setContentPane(oldArchive);
					frame.revalidate();
				}
			}
		});
	}

	public void makeRestore() {
		restore = new JPanel();
		restore.setBackground(w);
		restore.setLayout(new BoxLayout(restore, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("Restore");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		ArrayList<State> states = mainframe.getArchive(selectedArchiveIndex).states;
		ArrayList<String> stateDisplays = new ArrayList<String>();
		for (int i = 0; i < states.size(); i++) {
			stateDisplays.add("State " + states.get(i).getID());
		}
		String text = "<html><center>" + stateDisplays.get(stateIndex) + " has been selected for restoration. "
				+ "Are you sure you want to override the current data to an earlier version? <br>";
		if (restoreLocation == mainframe.getArchive(selectedArchiveIndex).getDataPath()
				&& (stateIndex < (stateDisplays.size() - 1))) {
			if (stateDisplays.size() - stateIndex > 5) {
				text += "WARNING the newer states will be overwritten!<br>";
			} else {
				text += "WARNING the following states will be overwritten:<br>";
				for (int i = stateIndex + 1; i < stateDisplays.size(); i++) {
					text += stateDisplays.get(i);
					if (i < stateDisplays.size() - 1)
						text += " - ";
				}
			}
		}
		text += "</center></html>";
		JLabel message = new JLabel(text, SwingConstants.CENTER);
		message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		message.setForeground(b);
		restore.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(50, 20, 100, 20));
		restore.add(message);
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		message.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
		restore.setPreferredSize(new Dimension(1100, 700));
	}

	public void makeTrim(boolean repaint) {
		trim = new JPanel();
		trim.setBackground(w);
		trim.setLayout(new BoxLayout(trim, BoxLayout.Y_AXIS));
		trim.setBorder(BorderFactory.createEmptyBorder(0, 20, 70, 20));
		JLabel title = new JLabel("Trim Behavior");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 100));
		title.setForeground(b);
		JLabel prompt = new JLabel();
		prompt.setText("How many versions do you want to keep?");
		prompt.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		prompt.setForeground(b);
		trimBehavior = new JTextField(3);
		trimBehavior.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		trimBehavior.setMinimumSize(new Dimension(400, 150));
		if (Integer.parseInt(mainframe.getArchive(selectedArchiveIndex).trimAmnt()) == 9999) {
			trimBehavior.setText("1      ");
		} else
			trimBehavior.setText(mainframe.getArchive(selectedArchiveIndex).trimAmnt());

		JLabel versions = new JLabel();
		versions.setText("versions");
		versions.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		versions.setForeground(b);
		trimNum = new JPanel();
		trimNum.setBackground(w);
		trimNum.setLayout(new FlowLayout());
		trimNum.add(prompt);
		prompt.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 20));
		trimNum.add(trimBehavior);
		trimNum.add(versions);
		JPanel gridPane = new JPanel();
		gridPane.setLayout(new GridLayout(1, 2, 50, 50));
		gridPane.setBackground(w);
		JRadioButton radio1 = new JRadioButton("Manual Trim");
		radio1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		radio1.setForeground(b);
		radio1.setBackground(w);
		JRadioButton radio2 = new JRadioButton("Automatic Trim");
		radio2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
		radio2.setForeground(b);
		radio2.setBackground(w);
		radio2.setSelected(true);
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(1, 2, 50, 10));
		p.setBackground(w);
		ButtonGroup group = new ButtonGroup();
		group.add(radio1);
		group.add(radio2);
		gridPane.add(radio1);
		gridPane.add(radio2);
		trim.add(backButton());
		trim.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		trim.add(gridPane);
		gridPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		gridPane.setBorder(BorderFactory.createEmptyBorder(50, 300, 20, 300));
		trim.add(trimNum);
		if (!repaint) {
			if (Integer.parseInt(mainframe.getArchive(selectedArchiveIndex).trimAmnt()) == 9999) {
				trimBehavior.setText("0");
				trimNum.setVisible(false);
				trimBehavior.setVisible(false);
				radio1.setSelected(true);
				frame.revalidate();
			} else {
				trimBehavior.setText(mainframe.getArchive(selectedArchiveIndex).trimAmnt());
				trimBehavior.setVisible(true);
			}
		}
		radio1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trimBehavior.setText("0");
				trimNum.setVisible(false);
				trimBehavior.setVisible(false);
				// oldArchive.removeAll();
				makeOldArchive();
				currPage = Page.TRIM;
				trim.revalidate();
				frame.setContentPane(trim);
				frame.revalidate();
			}
		});
		radio2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeOldArchive();
				makeTrim(true);
				currPage = Page.TRIM;
				trim.revalidate();
				frame.setContentPane(trim);
				frame.revalidate();
			}
		});
	}

	public void makeAutoConfirmation() {
		autoConfirmation = new JPanel();
		autoConfirmation.setBackground(w);
		autoConfirmation.setLayout(new BoxLayout(autoConfirmation, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("Automatic Trim Confirmation");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JLabel message = new JLabel("<html><center>You have selected to keep " + trimBehavior.getText()
				+ " version(s) with Automatic Trim. Do you wish to continue with this decision?</center></html>",
				SwingConstants.CENTER);
		message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		message.setForeground(b);
		autoConfirmation.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(50, 20, 100, 20));
		autoConfirmation.add(message);
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		message.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
		autoConfirmation.setPreferredSize(new Dimension(1200, 700));
	}

	public void makeBackUp() {
		backUp = new JPanel();
		currPage = Page.BACKUP;
		backUp.setBackground(w);
		backUp.setLayout(new BoxLayout(backUp, BoxLayout.Y_AXIS));
		backUp.setBorder(BorderFactory.createEmptyBorder(0, 20, 70, 20));
		JLabel title = new JLabel("Back Up");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		String trimmedStates = mainframe.getArchive(selectedArchiveIndex).potentialTrim();
		JLabel message;
		if (trimmedStates.equals("")) {
			message = new JLabel("", SwingConstants.CENTER);
		} else {
			message = new JLabel(
					"<html><center>" + "WARNING: You have automatic trim behavior on. "
							+ "If you back up now, you will lose " + trimmedStates + "</center></html>",
					SwingConstants.CENTER);
		}
		message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		message.setForeground(b);
		JButton bBackUp = new JButton();
		bBackUp.setText("Back Up");
		bBackUp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		bBackUp.setBackground(b);
		bBackUp.setForeground(w);
		backUp.add(backButton());
		backUp.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
		backUp.add(message);
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		message.setBorder(BorderFactory.createEmptyBorder(50, 50, 100, 50));
		backUp.add(bBackUp);
		bBackUp.setAlignmentX(Component.CENTER_ALIGNMENT);

		bBackUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeProgressBar();
				popup.setTitle("Backup Progress Bar");
				Runnable r = new Runnable() {
					public void run() {
						t.start();
					}
				};
				Runnable backup = new Runnable() {
					public void run() {
						try {
							mainframe.getArchive(selectedArchiveIndex).backUp();
							popup.dispose();
							t.stop();
							mainframe.getArchive(selectedArchiveIndex).resetLoadingBar();
							currPage = Page.BACKUP;
							makeOldArchive();
							frame.setContentPane(oldArchive);
							frame.revalidate();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				Thread update = new Thread(r);
				update.start();
				Thread process = new Thread(backup);
				process.start();
				process.setPriority(10);
				popup.revalidate();
				makeBackUp();
				frame.revalidate();
			}
		});
	}

	public void makeRecovery() {
		recovery = new JPanel();
		recovery.setBackground(w);
		recovery.setLayout(new BoxLayout(recovery, BoxLayout.Y_AXIS));
		recovery.setBorder(BorderFactory.createEmptyBorder(0, 50, 70, 50));
		JLabel title = new JLabel("Recovery");
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 70));
		title.setForeground(b);
		JLabel message = new JLabel("<html><center>There has been a unexpected shutdown of the "
				+ "system or an error. What would you like to do?</center></html>", SwingConstants.CENTER);
		message.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		message.setForeground(b);
		JButton replace = new JButton();
		replace.setText("<html><center>Replace incomplete<br>version with<br>new backup</center></html>");
		replace.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		replace.setBackground(b);
		replace.setForeground(w);
		JButton delete = new JButton();
		delete.setText("<html><center>Delete all<br>partial versions</html></center>");
		delete.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		delete.setBackground(b);
		delete.setForeground(w);
		JButton close = new JButton();
		close.setText("Close Archive");
		close.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		close.setBackground(b);
		close.setForeground(w);
		JButton exit = new JButton();
		exit.setText("Exit Sync Tool");
		exit.addActionListener(this);
		exit.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		exit.setBackground(b);
		exit.setForeground(w);
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(1, 3, 100, 100));
		container.setBackground(w);
		container.add(replace);
		container.add(delete);
		container.add(close);
		container.setBorder(BorderFactory.createEmptyBorder(150, 50, 150, 50));
		recovery.add(title);
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50));
		recovery.add(message);
		message.setAlignmentX(Component.CENTER_ALIGNMENT);
		recovery.add(container);
		container.setAlignmentX(Component.CENTER_ALIGNMENT);
		recovery.add(exit);
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		exit.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
		replace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainframe.getArchive(selectedArchiveIndex).cleanUp(true);
				makeOldArchive();
				currPage = Page.OLD_ARCHIVE;
				frame.setContentPane(oldArchive);
				frame.revalidate();
			}
		});
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainframe.getArchive(selectedArchiveIndex).cleanUp(false);
				makeOldArchive();
				currPage = Page.OLD_ARCHIVE;
				frame.setContentPane(oldArchive);
				frame.revalidate();
			}
		});
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainframe.closeArchive();
				currPage = Page.ARCHIVE_LIST;
				frame.setContentPane(archiveList);
				frame.revalidate();
			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if ((valid) && e.getActionCommand().trim().equals("Trim")) {
			JButton[] buttons = { new JButton("Cancel"), new JButton("Continue") };
			buttons[0].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.getRootFrame().dispose();
				}
			});
			buttons[1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.getRootFrame().dispose();
					int counter = 0;
					// We can only get button model from buttongroup so hard to identify
					// Therefore we extract all the button models from the buttongroup to find the
					// correct index;
					// Enumeration is an imported class very similar to an arraylist
					Enumeration<AbstractButton> buttonList = group.getElements();
					for (Enumeration<AbstractButton> buttons = buttonList; buttons.hasMoreElements();) {
						AbstractButton button = buttons.nextElement();

						if (button.isSelected()) {
							stateIndex = counter;
						}
						counter++;
					}
					makeProgressBar();
					popup.setTitle("Trim Progress Bar");
					Runnable r = new Runnable() {
						public void run() {
							t.start();
						}
					};
					Runnable backup = new Runnable() {
						public void run() {
							mainframe.getArchive(selectedArchiveIndex).trimState(stateIndex);
							popup.dispose();
							t.stop();
							mainframe.getArchive(selectedArchiveIndex).resetLoadingBar();
							makeOldArchive();
							frame.setContentPane(oldArchive);
							frame.revalidate();
						}
					};
					Thread update = new Thread(r);
					update.start();
					Thread process = new Thread(backup);
					process.start();
					process.setPriority(10);
					popup.revalidate();
				}
			});
			makeConfirmation();
			popUp(confirmation, "Trim Confirmation", buttons);
		} else if (e.getActionCommand().trim().equals("Restore")) {
			JButton[] buttons = { new JButton("No"), new JButton("Yes") };
			buttons[0].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.getRootFrame().dispose();
				}
			});
			buttons[1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JOptionPane.getRootFrame().dispose();
					int counter = 0;
					// We can only get button model from buttongroup so hard to identify
					// Therefore we extract all the button models from the buttongroup to find the
					// correct index;
					// Enumeration is an imported class very similar to an arraylist
					Enumeration<AbstractButton> buttonList = group.getElements();
					for (Enumeration<AbstractButton> buttons = buttonList; buttons.hasMoreElements();) {
						AbstractButton button = buttons.nextElement();

						if (button.isSelected()) {
							stateIndex = counter;
						}
						counter++;
					}
					makeProgressBar();
					popup.setTitle("Restore Progress Bar");
					Runnable r = new Runnable() {
						public void run() {
							t.start();
						}
					};
					Runnable backup = new Runnable() {
						public void run() {
							if (restoreLocation.equals(mainframe.getArchive(selectedArchiveIndex).getDataPath())) {
								mainframe.getArchive(selectedArchiveIndex).restore(stateIndex);
							} else {
								mainframe.getArchive(selectedArchiveIndex).restore(stateIndex, restoreLocation);
							}
							popup.dispose();
							t.stop();
							mainframe.getArchive(selectedArchiveIndex).resetLoadingBar();
							currPage = Page.OLD_ARCHIVE;
							makeOldArchive();
							frame.setContentPane(oldArchive);
							frame.revalidate();
						}
					};
					Thread update = new Thread(r);
					update.start();
					Thread process = new Thread(backup);
					process.start();
					process.setPriority(10);
					popup.revalidate();
				}
			});
			if (valid) {
				makeRestore();
				popUp(restore, "Restore Confirmation", buttons);
			}
		}
		frame.revalidate();
	}

	public void popUp(JPanel p, String title, JButton[] buttons) {
		JOptionPane.showOptionDialog(null, p, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
				buttons, null);
	}

	public JPanel backButton() {
		JPanel backButton = new JPanel();
		final JButton back = new JButton();
		back.setText("  Back  ");
		back.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		back.setBackground(b);
		back.setForeground(w);
		final JButton[] buttons = { new JButton("No"), new JButton("Yes") };
		buttons[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.getRootFrame().dispose();
			}
		});
		buttons[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.getRootFrame().dispose();
				mainframe.getArchive(selectedArchiveIndex).changeTrim(Integer.parseInt(trimBehavior.getText().trim()));
				makeSettings();
				makeTrim(false);
				currPage = Page.SETTINGS;
				frame.setContentPane(settings);
				frame.revalidate();
			}
		});
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((!(trimBehavior.getText().isEmpty()))
						&& (currPage.equals(Page.TRIM) && (Integer.parseInt(trimBehavior.getText().trim())) > 0)
						&& currPage == Page.TRIM) {
					autoConfirmation.removeAll();
					makeAutoConfirmation();
					popUp(autoConfirmation, "Automatic Trim Confirmation", buttons);
				} else if ((!(trimBehavior.getText().isEmpty())) && (Integer.parseInt(trimBehavior.getText()) == 0)
						&& currPage == Page.TRIM) {
					mainframe.getArchive(selectedArchiveIndex).changeTrim(9999);
					makeSettings();
					currPage = Page.SETTINGS;
					frame.setContentPane(settings);
					frame.revalidate();
				} else {
					if (currPage == Page.ARCHIVE_LIST) {
						currPage = Page.START;
						frame.setContentPane(start);
					} else if (currPage == Page.NEW_ARCHIVE || currPage == Page.OPEN_FILE
							|| currPage == Page.OLD_ARCHIVE) {
						currPage = Page.ARCHIVE_LIST;
						makeArchiveList();
						frame.setContentPane(archiveList);
					} else if (currPage == Page.MODIFICATION_REPORT || currPage == Page.BACKUP
							|| currPage == Page.SETTINGS || currPage == Page.RESTORE_LOC) {
						currPage = Page.OLD_ARCHIVE;
						makeOldArchive();
						frame.setContentPane(oldArchive);
					} else if (currPage == Page.TRIM) {
						makeSettings();
						currPage = Page.SETTINGS;
						frame.setContentPane(settings);
					}
					frame.revalidate();
				}
			}
		});

		backButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 1300));
		backButton.add(back);
		backButton.setMaximumSize(new Dimension(Short.MAX_VALUE, 100));
		backButton.setBackground(w);

		return backButton;
	}

	public void makeProgressBar() {
		bar = new ProgressBar();
		popup = new JFrame();
		popup.setContentPane(bar);
		popup.setBackground(w);
		popup.setVisible(true);
		bar.setVisible(true);
		popup.setSize(1100, 500);
		popup.setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		Visual v = new Visual();
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(start);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBackground(w);
		frame.setVisible(true);
		UIManager.put("OptionPane.background", w);
		UIManager.put("Panel.background", w);
	}

	class ProgressBar extends JPanel {
		private int counter = 0;
		private int limit = 1000;

		@Override
		public void paintComponent(Graphics g) {
			if (counter < mainframe.getArchive(selectedArchiveIndex).getLoadingValue() * 10)
				counter += 10;
			if (mainframe.getArchive(selectedArchiveIndex).getLoadingValue() == 100) {
				counter = 1000;
			}
			g.setColor(new Color(250, 250, 250));
			g.fillRect(0, 0, 1050, 250);
			g.setColor(new Color(100, 150, 200));
			g.fillRect(10, 10, limit + 20, 100);
			g.fillRect(70, 180, 900, 100);
			g.setColor(new Color(250, 250, 250));
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
			g.drawString("The Process is " + (counter * 100 / limit) + "% Complete", 100, 250);

			g.setColor(new Color(250, 250, 250));
			g.fillRect(20 + counter, 15, limit + 5 - counter, 90);
			g.setColor(new Color(100, 200, 150));
			g.fillRect(15, 15, counter, 90);
		}

		public void iterate() throws InterruptedException {
			while (true && counter <= limit) {
				// Thread.sleep(100);
				repaint();
				counter += 10;
			}
		}
	}
}

class HintText extends JTextField implements FocusListener {
	private final String hint;
	private boolean showingHint;

	public HintText(final String hint) {
		super(hint);
		this.hint = hint;
		this.showingHint = true;
		super.addFocusListener(this);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (this.getText().isEmpty()) {
			super.setText("");
			showingHint = false;
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (this.getText().isEmpty()) {
			super.setText(hint);
			showingHint = true;
		}
	}

	@Override
	public String getText() {
		return showingHint ? "" : super.getText();
	}
}
