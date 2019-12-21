package eg.edu.alexu.csd.oop.game;

import javax.swing.*;

public class Difficulty extends Command {


        private Buttongenerator buttonGenerator;
        private JFrame frame;

        Difficulty(ScreenResolution resolution, JMenuBar menuBar) {
            super(resolution, menuBar);
        }

        void setButtonGenerator(Buttongenerator buttonGenerator) {
            this.buttonGenerator = buttonGenerator;
        }

        JFrame getFrame() {
            return frame;
        }

        public void  generate(){

            JPanel panel = new JPanel();
            panel.add(buttonGenerator.getE());
            panel.add(buttonGenerator.getM());
            panel.add(buttonGenerator.getH());
            panel.setVisible(true);
            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }

        void setFrame() {
            frame = new JFrame();
            frame.setSize(300,100);
        }
    }


