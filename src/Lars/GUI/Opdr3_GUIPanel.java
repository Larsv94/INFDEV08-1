package Lars.GUI;

import Lars.INFDEV08.Opdr3_FloodMap;
import Lars.Util.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Lars on 6/18/2015.
 */
public class Opdr3_GUIPanel extends JPanel {

    Dimension size;

    protected JPanel InteractingPanel = new JPanel();//contains user controls
    protected JPanel SketchPanel = new JPanel();//contains the sketch
    protected Opdr3_FloodMap sketch;


    public Opdr3_GUIPanel( Dimension size, Opdr3_FloodMap sketch) {
        super(new BorderLayout());
        this.size = size;
        this.setSize(size);
        this.sketch = sketch;

        InteractingPanel.setPreferredSize((new Dimension(size.width / 5, size.height)));
        InteractingPanel.setBackground(Color.CYAN);
        InteractingPanel.setVisible(true);
        setUpButtons();

        this.add(InteractingPanel,BorderLayout.WEST);
        SketchPanel.setPreferredSize(new Dimension((size.width / 5) * 4, size.height));
        System.out.println(SketchPanel.getPreferredSize());
        sketch.SIZE = new Vector2D(SketchPanel.getPreferredSize().width,SketchPanel.getPreferredSize().height);
        sketch.init();
        SketchPanel.add(sketch);
        this.add(SketchPanel, BorderLayout.EAST);


        SketchPanel.setVisible(true);
        this.setVisible(true);






    }

    private void setUpButtons(){

        JPanel speedPanel = new JPanel();//user controls for the speed control
        speedPanel.setLayout(new BoxLayout(speedPanel,BoxLayout.PAGE_AXIS));
        JPanel buttonPanel = new JPanel();
        final JTextArea speedText = new JTextArea(2,20);
        speedText.setText("1m per uur");
        speedText.setEditable(false);

        final JButton[] speedButtons = new JButton[]{new JButton("0.5m per uur"),new JButton("1m per uur "),new JButton("2m per uur")};//Array with the buttons

        //create and add buttons to speedpanel
        for (int i = 0; i < speedButtons.length ; i++) {
            speedButtons[i].setSize(100,50);
            speedButtons[i].setVisible(true);
            buttonPanel.add(speedButtons[i]);
        }
        speedButtons[0].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sketch.setStep(0.5f);
                speedText.setText(speedButtons[0].getText());
            }
        });
        speedButtons[1].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sketch.setStep(1f);
                speedText.setText(speedButtons[1].getText());
            }
        });
        speedButtons[2].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sketch.setStep(2f);
                speedText.setText(speedButtons[2].getText());
            }
        });
        speedPanel.add(buttonPanel);
        speedPanel.add(speedText);
        speedPanel.setVisible(true);

        InteractingPanel.add(speedPanel);//add speed panel to the panel with all the usercontrols



        JPanel controlPanel = new JPanel();//user controls for the animation state
        controlPanel.setLayout(new FlowLayout());
        controlPanel.setVisible(true);


        final JButton[] controlButtons = new JButton[]{new JButton("play"),new JButton("pause"),new JButton("stop")};//array with the buttons

        //create and add buttons
        for (int i = 0; i < controlButtons.length ; i++) {
            controlButtons[i].setSize(InteractingPanel.getSize().width/controlButtons.length,controlPanel.getSize().height-10);
            controlButtons[i].setVisible(true);
            controlPanel.add(controlButtons[i]);
        }
        controlButtons[0].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sketch.playDraw();
                controlButtons[0].setEnabled(false);
                controlButtons[1].setEnabled(true);
                controlButtons[2].setEnabled(true);
            }
        });
        controlButtons[1].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sketch.pauzeDraw();
                controlButtons[0].setEnabled(true);
                controlButtons[1].setEnabled(false);
                controlButtons[2].setEnabled(true);

            }
        });
        controlButtons[1].setEnabled(false);
        controlButtons[2].addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sketch.stopDraw();
                controlButtons[0].setEnabled(true);
                controlButtons[1].setEnabled(false);
                controlButtons[2].setEnabled(false);
            }
        });
        controlButtons[2].setEnabled(false);
        InteractingPanel.add(controlPanel);


        JButton saveJPG = new JButton("Save image as JPG");
        saveJPG.setPreferredSize(new Dimension(200, 20));
        saveJPG.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlButtons[1].doClick();
                sketch.saveImage();
            }
        });
        InteractingPanel.add(saveJPG);

    }
}
