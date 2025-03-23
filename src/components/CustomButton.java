package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {
    private Color defaultColor;
    private Color hoverColor;
    private Color pressedColor;
    private Font buttonFont;
    private int cornerRadius;
    
    public CustomButton(String text, Color defaultColor, Color hoverColor, Color pressedColor, Font font, int cornerRadius) {
        super(text);
        this.defaultColor = defaultColor;
        this.hoverColor = hoverColor;
        this.pressedColor = pressedColor;
        this.buttonFont = font;
        this.cornerRadius = cornerRadius;
        
        setupButton();
    }
    
    public CustomButton(String text) {
        this(text, new Color(59, 89, 182), new Color(48, 79, 160), new Color(38, 69, 140), 
             new Font("Arial", Font.BOLD, 16), 15);
    }
    
    private void setupButton() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(buttonFont);
        setForeground(Color.WHITE);
        
        // Add mouse listeners for hover and click effects
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultColor);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hoverColor);
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getModel().isPressed()) {
            g2.setColor(pressedColor);
        } else if (getModel().isRollover()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(defaultColor);
        }
        
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.dispose();
        
        super.paintComponent(g);
    }
    
    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.width += 20; // Add some padding
        size.height += 10;
        return size;
    }
}
