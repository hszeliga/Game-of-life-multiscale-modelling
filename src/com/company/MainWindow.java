package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainWindow extends JFrame{
    private DataManager dm;
    private JPanel mainPanel;
    private JCanvasPanel canvasPanel;
    private JPanel buttonPanel;

    private JLabel widthLabel;
    private JSpinner widthSpinner;

    private JLabel heightLabel;
    private JSpinner heightSpinner;
    private JButton randomButton;

    private JLabel gap;
    private JButton stillLifeButton;
    private JButton gliderButton;
    private JButton oscillatorButton;
    private JButton ownDefinitionButton;
    private JButton applyButton;
    private JButton stopButton;


    public MainWindow (String title) {
        super(title);

        dm = new DataManager(1000, 500);

        try {
            BufferedImage bg = ImageIO.read(new File("blank.bmp"));
            dm.img = bg;
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        widthLabel = new JLabel("Width:");
        SpinnerNumberModel widthModel = new SpinnerNumberModel(1000, 0, 1500, 10);
        widthSpinner = new JSpinner(widthModel);

        heightLabel = new JLabel("Height: ");
        SpinnerNumberModel heightModel = new SpinnerNumberModel(500, 0, 1000, 10);
        heightSpinner = new JSpinner(heightModel);
        randomButton = new JButton("Random");

        gap = new JLabel("");

        stillLifeButton = new JButton("Still life");
        gliderButton = new JButton("Glider");
        oscillatorButton = new JButton("Oscillator");
        ownDefinitionButton = new JButton("Own definition");
        applyButton = new JButton("Apply");
        stopButton = new JButton("Stop");
        stopButton.setVisible(false);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(15, 1));


        buttonPanel.add(stillLifeButton);
        buttonPanel.add(gliderButton);
        buttonPanel.add(oscillatorButton);
        buttonPanel.add(ownDefinitionButton);

        buttonPanel.add(gap);

        buttonPanel.add(widthLabel);
        buttonPanel.add(widthSpinner);
        buttonPanel.add(heightLabel);
        buttonPanel.add(heightSpinner);
        buttonPanel.add(randomButton);

        buttonPanel.add(gap);

        buttonPanel.add(applyButton);
        buttonPanel.add(stopButton);


        Utility util = new Utility(dm);

        canvasPanel = new JCanvasPanel(dm);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(BorderLayout.CENTER, canvasPanel);
        mainPanel.add(BorderLayout.EAST, buttonPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);

        this.setSize(new Dimension(1500, 1000));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);


        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dm.img = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
                dm.width = 1;
                dm.height = 1;
                dm.pixels = new int[1][1];
                util.generateRandom();

                dm.img = new BufferedImage((Integer) widthSpinner.getValue(), (Integer) heightSpinner.getValue(), BufferedImage.TYPE_BYTE_GRAY);
                dm.width = (Integer) widthSpinner.getValue();
                dm.height = (Integer) heightSpinner.getValue();
                dm.pixels = new int[dm.width][dm.height];
                util.generateRandom();

                canvasPanel.repaint();
            }
        });


        stillLifeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedImage bg = ImageIO.read(new File("niezmienne.bmp"));
                    dm.img = bg;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                canvasPanel.repaint();

            }
        });

        gliderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedImage bg = ImageIO.read(new File("glider.bmp"));
                    dm.img = bg;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                canvasPanel.repaint();

            }
        });

        oscillatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedImage bg = ImageIO.read(new File("oscylator.bmp"));
                    dm.img = bg;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                canvasPanel.repaint();

            }
        });

        ownDefinitionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedImage bg = ImageIO.read(new File("pepe.bmp"));
                    dm.img = bg;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                canvasPanel.repaint();

            }
        });


        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applyButton.setVisible(false);
                stopButton.setVisible(true);
                int tempWidth=(Integer) widthSpinner.getValue();
                int tempHeight=(Integer) heightSpinner.getValue();

                final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        util.gameOfLife();

                    }
                }, 0, 1, TimeUnit.MILLISECONDS);



                final ScheduledExecutorService executorService1 = Executors.newSingleThreadScheduledExecutor();
                executorService1.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {

                        canvasPanel.repaint();
                        if(stopButton.getModel().isPressed()){
                            executorService.shutdown();
                            executorService1.shutdown();

                            applyButton.setVisible(true);
                            stopButton.setVisible(false);

                            canvasPanel.repaint();

                        }

                        else if((Integer)widthSpinner.getValue() !=tempWidth || (Integer) heightSpinner.getValue() !=tempHeight)  {

                                    executorService.shutdown();
                                    executorService1.shutdown();
                                }
                    }
                }, 0, 1, TimeUnit.MILLISECONDS);

            }
        });
    }

    public static void main(String[] args){
        MainWindow mw = new MainWindow("App");
        mw.setVisible(true);
        mw.canvasPanel.repaint();
    }
}
