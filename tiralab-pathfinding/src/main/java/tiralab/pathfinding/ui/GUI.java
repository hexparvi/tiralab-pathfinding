package tiralab.pathfinding.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import tiralab.pathfinding.Astar;
import tiralab.pathfinding.Heuristic;
import tiralab.pathfinding.JPS;
import tiralab.pathfinding.Pathfinder;
import tiralab.pathfinding.domain.Maze;
import tiralab.pathfinding.domain.Node;
import tiralab.pathfinding.io.MyIO;
import tiralab.pathfinding.test.Test;
import tiralab.pathfinding.util.NodeStack;

/**
 *
 */
public class GUI extends JFrame {
    
    private JRadioButton jps = new JRadioButton("JPS");
    private JRadioButton dijkstra = new JRadioButton("Dijkstra");
    private JRadioButton astar = new JRadioButton("A*");
    
    private JRadioButton maze = new JRadioButton("Maze");
    private JRadioButton cave = new JRadioButton("Cave");
    
    private JTextField startXCoord = new JTextField(4);
    private JTextField startYCoord = new JTextField(4);
    private JTextField endXCoord = new JTextField(4);
    private JTextField endYCoord = new JTextField(4);
    
    private JLabel mapImg = new JLabel();
    private JLabel timeLabel = new JLabel("Time spent: ");
    private JLabel nodeLabel = new JLabel("Nodes visited: ");
    private JLabel pathLabel = new JLabel("Path length: ");
    
    private JRadioButton testRun = new JRadioButton("Test");
    private JRadioButton normalRun = new JRadioButton("Run");
    private JComboBox times = new JComboBox(new Integer[]{10, 100});
    
    Pathfinder pathfinder;
    
    public GUI() {
        JPanel topPanel = new JPanel(); // map
        topPanel.setBackground(Color.gray);
        JPanel mapPanel = new JPanel(); // map
        mapPanel.setBackground(Color.blue);
        JPanel resultsPanel = new JPanel(); // results
        resultsPanel.setBackground(Color.cyan);
        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.green);
        JPanel algoPanel = new JPanel();
        algoPanel.setBackground(Color.pink);
        JPanel mazePanel = new JPanel();
        mazePanel.setBackground(Color.black);
        JPanel coordPanel = new JPanel();
        coordPanel.setBackground(Color.gray);
        JPanel runPanel = new JPanel();
        runPanel.setBackground(Color.white);
        
        bottomPanel.add(mazePanel);
        bottomPanel.add(algoPanel);
        bottomPanel.add(coordPanel);
        bottomPanel.add(runPanel);
        
        GridLayout bottomLayout = new GridLayout(0, 4);
        bottomPanel.setLayout(bottomLayout);
        
        topPanel.add(mapPanel);
        
        FlowLayout topLayout = new FlowLayout();
        topPanel.setLayout(topLayout);
        
        //top panel setup
        BufferedImage img = MyIO.getBufferedImageFromFilepath("src/mazes/maze1.png");
        mapImg.setIcon(new ImageIcon(img));
        
        mapImg.addMouseListener(new CoordinateListener());
        
        mapPanel.add(mapImg);
        resultsPanel.add(timeLabel);
        resultsPanel.add(nodeLabel);
        resultsPanel.add(pathLabel);
        
        //bottom panel setup
        
        JLabel algoLabel = new JLabel("Select algorithm:");
        ButtonGroup algoGroup = new ButtonGroup();
        algoGroup.add(dijkstra);
        algoGroup.add(astar);
        algoGroup.add(jps);
        
        JLabel mapLabel = new JLabel("Select map:");
        ButtonGroup mapGroup = new ButtonGroup();
        maze.addActionListener(new MapSelectionListener());
        cave.addActionListener(new MapSelectionListener());
        mapGroup.add(maze);
        mapGroup.add(cave);
        
        JLabel coordLabel1 = new JLabel("Start:");
        JLabel coordLabel2 = new JLabel("End:");
        coordPanel.add(coordLabel1);
        coordPanel.add(startXCoord);
        coordPanel.add(startYCoord);
        coordPanel.add(coordLabel2);
        coordPanel.add(endXCoord);
        coordPanel.add(endYCoord);
        
        dijkstra.setSelected(true);
        maze.setSelected(true);
        
        algoPanel.add(algoLabel);
        algoPanel.add(dijkstra);
        algoPanel.add(astar);
        algoPanel.add(jps);
        mazePanel.add(mapLabel);
        mazePanel.add(maze);
        mazePanel.add(cave);
        
        JButton runButton = new JButton("Run");
        setSize(400,500);
        runButton.addActionListener(new RunListener());
        
        JPanel timesPanel = new JPanel();
        JLabel timesLabel = new JLabel("Times: ");
        timesPanel.add(timesLabel);
        timesPanel.add(times);
        JButton runTestsButton = new JButton("Run Tests");
        runTestsButton.addActionListener(new RunListener());
        
        ButtonGroup runGroup = new ButtonGroup();
        runGroup.add(normalRun);
        runGroup.add(testRun);
        normalRun.setSelected(true);
        
        runPanel.add(normalRun);
        runPanel.add(testRun);
        runPanel.add(timesPanel);
        runPanel.add(runButton);
        
        add(resultsPanel, BorderLayout.PAGE_START);
        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);
        
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    
    class MapSelectionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (maze.isSelected()) {
                BufferedImage newMapImg = MyIO.getBufferedImageFromFilepath("src/mazes/maze1.png");
                mapImg.setIcon(new ImageIcon(newMapImg));
                
            } else {
                BufferedImage newMapImg = MyIO.getBufferedImageFromFilepath("src/mazes/cave1.png");
                mapImg.setIcon(new ImageIcon(newMapImg));
            }
        }
        
    }
    
    class RunListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Maze map;
            Node start = new Node("");
            Node end = new Node("");
        
            if (jps.isSelected()) {
                pathfinder = new JPS();
            
            } else if (astar.isSelected()) {
                pathfinder = new Astar(new Heuristic("euclidean"));
            
            } else {
                pathfinder = new Astar(new Heuristic(""));
            }
        
            if (maze.isSelected()) {
                int[][] pixelArray = MyIO.readFromFile("src/mazes/maze1.png");
                map = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
                map.generateNodes();
            
            } else {
                int[][] pixelArray = MyIO.readFromFile("src/mazes/cave1.png");
                map = new Maze(pixelArray, pixelArray.length, pixelArray[0].length);
                map.generateNodes();
            }
        
            if (!startXCoord.getText().equals("") && !endXCoord.getText().equals("") &&
                    !startYCoord.getText().equals("") && !endYCoord.getText().equals("")) {
                start = map.getNodeAtPosition(Integer.parseInt(startXCoord.getText()),
                        Integer.parseInt(startYCoord.getText()));
                end = map.getNodeAtPosition(Integer.parseInt(endXCoord.getText()),
                        Integer.parseInt(endYCoord.getText()));
            }
        
            if (normalRun.isSelected()) {
                runAlgo(map, start, end);
            
            } else if (testRun.isSelected()) {
                runTests(map, start, end);
            }
        }
    
        private void runAlgo(Maze map, Node start, Node end) {
            long startTime = System.nanoTime();
            pathfinder.run(map, start, end);
            long endTime = System.nanoTime();
            long runTime = (endTime - startTime) / 1000000 ;
        
            timeLabel.setText("Time spent: " + runTime + " ms");
            nodeLabel.setText("Nodes visited: " + pathfinder.getNumberOfNodesVisited());
            pathLabel.setText("Path length: " + pathfinder.getPathLength());
        
            int[][] newMap = drawPath(map, pathfinder.getPath());
            BufferedImage newMapImg = MyIO.getBufferedImageFromPixels(newMap);
            mapImg.setIcon(new ImageIcon(newMapImg));
        }
    
        private void runTests(Maze map, Node start, Node end) {
            int testRuns = (int) times.getSelectedItem();
        
            long[] results = Test.runTests(testRuns, map, start, end);
        
            timeLabel.setText("Time spent: Dijkstra " + results[0] + " ms, A* " 
                    + results[1] + " ms, JPS " + results[2] + " ms.");
            nodeLabel.setText("Nodes visited: N/A");
            pathLabel.setText("Path length: N/A");
        }
    
        private int[][] drawPath(Maze maze, NodeStack path) {
            int[][] pixelArray = maze.getPixels();
        
            while (path.size() > 0) {
                Node node = path.pop();
                pixelArray[node.getX()][node.getY()] = Color.RED.getRGB();
            }
        
            return pixelArray;
        }
    }
    
    class CoordinateListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (startXCoord.getText().equals("") || startYCoord.getText().equals("")) {
                startXCoord.setText(String.valueOf(e.getX()));
                startYCoord.setText(String.valueOf(e.getY()));
                
            } else if (endXCoord.getText().equals("") || endYCoord.getText().equals("")) {
                endXCoord.setText(String.valueOf(e.getX()));
                endYCoord.setText(String.valueOf(e.getY()));
            }
        }
    }
}