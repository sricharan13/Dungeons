package dungeon.view;

import dungeon.controller.DungeonGameViewController;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


class ConfigurationFrame extends JFrame {

  private static final int LABEL_FONT = 15;
  private static final int TF_FONT = 13;

  private final JTextField rowsTf;
  private final JTextField columnsTf;
  private final JTextField connectivityTf;
  private final JTextField itemPercentTf;
  private final JTextField wrapTf;
  private final JTextField monstersTf;
  private final JTextField testTf;
  private final JButton startGame;

  ConfigurationFrame() {
    setTitle("Dungeon Game Launcher");
    setSize(500, 500);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

    JPanel rowPanel = new JPanel(new FlowLayout());
    JLabel rows = new JLabel("Rows");
    rows.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT));
    rowPanel.add(rows);

    rowsTf = new JTextField(15);
    rowsTf.setFont(new Font("Arial", Font.PLAIN, TF_FONT));
    rowPanel.add(rowsTf);
    mainPanel.add(rowPanel);

    JPanel columnPanel = new JPanel(new FlowLayout());
    JLabel columns = new JLabel("Columns");
    columns.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT));
    columnPanel.add(columns);

    columnsTf = new JTextField(15);
    columnsTf.setFont(new Font("Arial", Font.PLAIN, TF_FONT));
    columnPanel.add(columnsTf);
    mainPanel.add(columnPanel);

    JPanel connectivityPanel = new JPanel(new FlowLayout());
    JLabel connectivity = new JLabel("Inter Connectivity");
    connectivity.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT));
    connectivityPanel.add(connectivity);

    connectivityTf = new JTextField(15);
    connectivityTf.setFont(new Font("Arial", Font.PLAIN, TF_FONT));
    connectivityPanel.add(connectivityTf);
    mainPanel.add(connectivityPanel);

    JPanel itemPercentPanel = new JPanel(new FlowLayout());
    JLabel itemPercent = new JLabel("Item Percentage");
    itemPercent.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT));
    itemPercentPanel.add(itemPercent);

    itemPercentTf = new JTextField(15);
    itemPercentTf.setFont(new Font("Arial", Font.PLAIN, TF_FONT));
    itemPercentPanel.add(itemPercentTf);
    mainPanel.add(itemPercentPanel);

    JPanel wrapPanel = new JPanel(new FlowLayout());
    JLabel wrap = new JLabel("Wrap");
    wrap.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT));
    wrapPanel.add(wrap);

    wrapTf = new JTextField(15);
    wrapTf.setFont(new Font("Arial", Font.PLAIN, TF_FONT));
    wrapPanel.add(wrapTf);
    mainPanel.add(wrapPanel);

    JPanel monstersPanel = new JPanel(new FlowLayout());
    JLabel monsters = new JLabel("Monsters");
    monsters.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT));
    monstersPanel.add(monsters);

    monstersTf = new JTextField(15);
    monstersTf.setFont(new Font("Arial", Font.PLAIN, TF_FONT));
    monstersPanel.add(monstersTf);
    mainPanel.add(monstersPanel);

    JPanel testPanel = new JPanel(new FlowLayout());
    JLabel test = new JLabel("Test");
    test.setFont(new Font("Arial", Font.PLAIN, LABEL_FONT));
    testPanel.add(test);

    testTf = new JTextField(15);
    testTf.setFont(new Font("Arial", Font.PLAIN, TF_FONT));
    testPanel.add(testTf);
    mainPanel.add(testPanel);

    startGame = new JButton("Start Game");
    startGame.setFont(new Font("Arial", Font.PLAIN, 15));
    mainPanel.add(startGame);

    add(mainPanel);

  }

  void addListener(DungeonGameViewController listener) {
    ActionListener actionListener = e -> {
      if (e.getActionCommand().equals("Start Game")) {
        String input = "new" + " " + getString(rowsTf) + " " + getString(columnsTf) + " "
                + getString(connectivityTf) + " " + getString(itemPercentTf) + " "
                + getString(wrapTf) + " " + getString(monstersTf) + " " + getString(testTf);
        listener.handleInput(input);
      }
    };
    startGame.addActionListener(actionListener);
  }

  private String getString(JTextField textField) {
    String text = textField.getText();
    textField.setText("");
    return text;
  }
}
