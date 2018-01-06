package Lars.GUI;
import Lars.INFDEV08.Opdr1_IcelandSketch;
import Lars.INFDEV08.Opdr2_ScatterMatrix;
import Lars.INFDEV08.Opdr3_FloodMap;
import Lars.Util.PAppletExtended;
import Lars.Util.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Lars on 6/10/2015.
 *
 * This is de JFrame that contains and renders all assignments
 *
 */
public class BaseFrame extends JFrame {

    public  final Dimension SIZE = new Dimension(1936,1056);

    PAppletExtended opdr1;
    PAppletExtended opdr2;
    Opdr3_GUIPanel opdr3_500;
    Opdr3_GUIPanel opdr3_1000;


    protected JPanel basePanel = new JPanel();

    public BaseFrame(){
        super("INFDEV08-1 Opdrachten - Lars Volkers - 0849685");
        this.setSize(this.SIZE);

        this.setExtendedState(Frame.MAXIMIZED_BOTH);//Maximize screen
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Set close button operation.

        //Create and add sketches
        defaultAddSketch((opdr1 = new Opdr1_IcelandSketch()));
        defaultAddSketch((opdr2 = new Opdr2_ScatterMatrix()));
        basePanel.add((opdr3_500 = new Opdr3_GUIPanel(SIZE, new Opdr3_FloodMap("src/data/hoogtedata500.txt", 1.05f))));
        basePanel.add((opdr3_1000 = new Opdr3_GUIPanel(SIZE, new Opdr3_FloodMap("src/data/hoogtedata1000.txt", 2.1f))));

        setUpMenuBar();
        basePanel.setVisible(true);
        this.add(basePanel);


        this.setVisible(true);

        //set the appropriate visibility per sketch.
        //This has to be done after the main frame is set to visible
        opdr1.setVisible(true);
        opdr2.setVisible(false);
        opdr3_500.setVisible(false);
        opdr3_1000.setVisible(false);

    }

    /**
     * Set's the menubar and the components for the menu
     */
    public void setUpMenuBar()
    {
        JMenuBar menuBar =new JMenuBar();
        JMenu menu = new JMenu("Opdrachten");
        JMenuItem panel1Item = new JMenuItem("Opdracht 1");
        JMenuItem panel2Item = new JMenuItem("Opdracht 2");
        final JMenuItem panel3Item = new JMenuItem("Opdracht 3 : 500km²");
        JMenuItem panel4Item = new JMenuItem("Opdracht 3 : 1000km²");

        panel1Item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opdr1.setVisible(true);
                opdr2.setVisible(false);
                opdr3_500.setVisible(false);
                opdr3_1000.setVisible(false);
            }
        });
        menu.add(panel1Item);
        menu.addSeparator();


        panel2Item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel3Item.doClick();//For some weird reason this is necessery to show panel 2 when the previous panel was panel 1. no other solution works -_-"
                opdr1.setVisible(false);
                opdr2.setVisible(true);
                opdr3_500.setVisible(false);
                opdr3_1000.setVisible(false);
            }
        });
        menu.add(panel2Item);
        menu.addSeparator();


        panel3Item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opdr1.setVisible(false);
                opdr2.setVisible(false);
                opdr3_500.setVisible(true);
                opdr3_1000.setVisible(false);
            }
        });
        menu.add(panel3Item);
        menu.addSeparator();


        panel4Item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                opdr1.setVisible(false);
                opdr2.setVisible(false);
                opdr3_500.setVisible(false);
                opdr3_1000.setVisible(true);
            }
        });
        menu.add(panel4Item);
        menu.addSeparator();

        menuBar.add(menu);
        this.setJMenuBar(menuBar);

    }

    /**
     * A generic method to add sketches that don't need extra gui components.
     * @param sketch The sketch that has to be added to this frame.
     */
    public void defaultAddSketch(PAppletExtended sketch){
        sketch.SIZE = new Vector2D(this.SIZE.width,this.SIZE.height);
        sketch.setVisible(true);
        sketch.init();


        basePanel.add(sketch);
    }

}
