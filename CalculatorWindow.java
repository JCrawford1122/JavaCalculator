import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class CalculatorWindow extends JFrame implements ActionListener, KeyListener
{
  // Instance Variables
  private JRadioButton btnBase2;
  private JRadioButton btnBase10;
  //array for keypad buttons
  private JButton [] numPadButtons = new JButton[9];
  private JButton zero;
  private JButton clear;
  private JButton back;
  private JTextField inputField;
  private JTextField base2Converted;
  private JTextField base10Converted;
  // value and length of inputField
  private String retrieveText;
  private int retrieveLength;
  public final int FIELD_LENGTH = 32;
  //test comment
  
  //Window Constructor
  public CalculatorWindow()
  {
    super("Base Conversion Calculator");
    this.setLayout(new BorderLayout());
    Container canvas = this.getContentPane();

 
    // Create Radio Buttons
    btnBase2 = new JRadioButton("Base 2");
    btnBase10 = new JRadioButton("base 10");

    
    // Create Radio Group
    ButtonGroup radioGroup = new ButtonGroup();
    radioGroup.add(btnBase2);
    radioGroup.add(btnBase10);

    // Create Buttons
    clear = new JButton("Clear");
    zero = new JButton("0");
    back = new JButton("Back");
 
    
    //Create Text Fields
    inputField = new JTextField(FIELD_LENGTH);
    base2Converted = new JTextField(FIELD_LENGTH);
    base2Converted.setEnabled(false);
    base2Converted.setBackground(Color.WHITE);
    base2Converted.setDisabledTextColor(Color.BLACK);
    base10Converted = new JTextField(FIELD_LENGTH);
    base10Converted.setEnabled(false);
    base10Converted.setBackground(Color.WHITE);
    base10Converted.setDisabledTextColor(Color.BLACK);
    
    // Add Action Listeners
    zero.addActionListener(this);
    clear.addActionListener(this);
    back.addActionListener(this);
    btnBase10.addActionListener(this);
    btnBase2.addActionListener(this);
    //numpad action listener created in numpad creation loop
    
    // listen for keyboard input
    inputField.addKeyListener(this);
   
    
    // Add panels to canvas
    canvas.add(createNorthPanel(), BorderLayout.NORTH);
    canvas.add(createNumPadPanel(), BorderLayout.WEST);
    canvas.add(createConvertedPanel(), BorderLayout.CENTER);
    
 
    
    // Base Window Settings
    this.setSize(600,300);
    this.setResizable(false);
    this.setLocation(600, 400);
    this.setVisible(true);
    // Starts the program with cursor inside inputField
    inputField.requestFocusInWindow();
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
  
  // Create North panel (mode selection, calc display)
  private JPanel createNorthPanel()
  {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setPreferredSize(new Dimension(0, 50));
    panel.add(createRadioPanel(), BorderLayout.WEST);
    panel.add(wrapMeInAPanel(inputField), BorderLayout.CENTER);
    JPanel border = new JPanel();
    border.setBackground(Color.BLACK);
    border.setPreferredSize(new Dimension(0, 1));
    panel.add(border, BorderLayout.SOUTH);
    return panel;
  }
  
  // Create radio button panel (located NW)
  private JPanel createRadioPanel()
  {
    JPanel panel = new JPanel(new GridLayout(2, 2));
    panel.add(new JLabel("  Entry Mode: "));
    panel.add(btnBase2);
    panel.add(new JPanel());
    panel.add(btnBase10);
    btnBase10.setSelected(true);
    return panel;
  }
  
  //Create numpad panel (located West)
  private JPanel createNumPadPanel()
  {
    JPanel panel = new JPanel(new GridLayout(4, 3, 5, 5));
    panel.setBackground(Color.LIGHT_GRAY);
    panel.setPreferredSize(new Dimension(210, 100));
    for(int i = 0; i <9; i++)
    {
      numPadButtons[i] = new JButton("" + (i+1));
      numPadButtons[i].addActionListener(this);
      panel.add(numPadButtons[i]);
    }
    panel.add(zero);
    panel.add(back);
    panel.add(clear);
    return panel;
  }
  
  //Create display panel for converted number (Center)
  private JPanel createConvertedPanel()
  {
    JPanel panel = new JPanel(new FlowLayout());
    panel.add(new JLabel("The number in base 10: "));
    panel.add(wrapMeInAPanel(base10Converted));
    panel.add(new JLabel("The number in base 2"));
    panel.add(wrapMeInAPanel(base2Converted));
    return panel;
  }
  
  private JPanel wrapMeInAPanel(Component c)
  { 
    JPanel panel = new JPanel();
    panel.add(c);
    return panel;
  }
  
  // Fills in the conversion boxes based on entry mode
  private void doConversions()
  {
    retrieveText = inputField.getText();
    retrieveLength = inputField.getText().length();
    
    if(btnBase10.isSelected())
    {
      base10Converted.setText(retrieveText);
      if(retrieveLength > 0)
      {
        base2Converted.setText(Integer.toBinaryString(Integer.valueOf(retrieveText)));
      }
    }
    if(btnBase2.isSelected())
    { 
      base2Converted.setText(retrieveText);
      if(retrieveLength > 0)
      {
        base10Converted.setText(Integer.valueOf(retrieveText, 2).toString());
      }
    }
    if(retrieveLength == 0)
    {
      base2Converted.setText("");
      base10Converted.setText("");
    }
  }
    
  public void actionPerformed(ActionEvent e)
  {
    retrieveText = inputField.getText();
    retrieveLength = inputField.getText().length();
    // numpad listener
    for(int i = 0; i < 9; i++)
    {
      if(e.getSource() == numPadButtons[i])
      {
        inputField.setText(retrieveText + (i+1));
      }
    }
    // zero button listener
    if(e.getSource() == zero)
    {
      inputField.setText(retrieveText + "0");
    }
    // clear button listener
    if(e.getSource() == clear)
    {
      inputField.setText("");  
    }
    // listener for back button
    // input field must not be empty
    if(e.getSource() == back && retrieveLength > 0 )
    {
      String string = retrieveText.substring(0, retrieveLength -1);
      inputField.setText(string);
      
    }
    // Only 1 and 0 are enabled for base2 entry mode
    if(e.getSource() == btnBase2)
    {
      for(int i = 1; i < 9; i++)
      { // disables 2-9
        numPadButtons[i].setEnabled(false);
      }
      // clears all fields when entry mode is changed
      clear.doClick();
    }
    // enable all numbers on numpad when base 10 is entry mode
    if(e.getSource() == btnBase10)
    {
      for(int i = 0; i < 9; i++)
      {
        numPadButtons[i].setEnabled(true);
      }
      // clears all fields when entry mode is changed
      clear.doClick();
    }
    this.doConversions();
  }
  
  @Override
  public void keyTyped(KeyEvent e)
  { 
  }

  @Override
  public void keyPressed(KeyEvent e)
  {   
  }
  // updates output fields when input is from keyboard
  @Override
  public void keyReleased(KeyEvent e)
  {
    this.doConversions();
    
  }
}
