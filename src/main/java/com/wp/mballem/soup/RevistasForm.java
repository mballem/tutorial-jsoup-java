package com.wp.mballem.soup;

import org.jsoup.Jsoup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Marcio Ballem
 * Date: 25/02/12
 * Time: 23:03
 * http://mballem.wordpress.com/
 */
public class RevistasForm extends JFrame {
    
    private JEditorPane editorPane;
    private JPanel panel1, panel2;
    private JLabel lbSrc, lbP;
    private JButton btnPrev, btnNext, btnOpen;
    
    private int position = 0;
    
    private List<HtmlParserRevistas> revistas;
    private static final String URL = "http://mballem.wordpress.com/revistas";

    public RevistasForm() throws HeadlessException, IOException {
        super("Tutorial Jsoup");
        Container frame = getContentPane();
        setLayout(null);

        revistas = new HtmlParserRevistas(Jsoup.connect(URL).get()).getParserRevistas();

        lbSrc = new JLabel();
        lbSrc.setSize(482,564);

        editorPane = new JEditorPane();
        editorPane.setContentType("html/text");
        editorPane.setSize(482,564);

        lbP = new JLabel();

        panel1 = new JPanel();
        panel1.setBounds(0,564,482,30);
        panel1.setBackground(Color.WHITE);

        addConteudo(position);

        btnPrev = new JButton("<< Prev ");
        btnOpen = new JButton("Open Browser");
        btnNext = new JButton(">> Next");

        panel2 = new JPanel();
        panel2.setBounds(0,590,482,35);
        panel2.setBackground(Color.GRAY);
        panel2.add(btnPrev);
        panel2.add(btnOpen);
        panel2.add(btnNext);

        frame.add(editorPane);
        frame.add(panel1);
        frame.add(panel2);

        setSize(488, 660);
        setVisible(true);
        setLocationRelativeTo(null);

        btnOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        openPageBrowser(position);
                    }
                };
                thread.start();
            }
        });

        btnPrev.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        addConteudo(prev());
                    }
                };
                thread.start();
            }
        });

        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        addConteudo(next());
                    }
                };
                thread.start();
            }
        });
    }

    private void addConteudo(int index) {
        try {
            lbSrc.setIcon(new ImageIcon(new URL(revistas.get(index).getSrc())));
            lbP.setText(revistas.get(index).getP());

            Thread.sleep(500);

            editorPane.add(lbSrc);
            panel1.add(lbP);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void openPageBrowser(int index) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.browse(new URI(revistas.get(index).getHref()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private int next() { // 0 1 2 3 4 5 6
        if (revistas.size()-1 == position) {
            position = 0;
            return position;
        } else {
            position = position + 1;
            return position;
        }
    }
    
    private int prev() {
        if (position == 0) {
            position = revistas.size() - 1;
            return position;
        } else {
            position = position - 1;
            return position;
        }
    }

    public static void main(String[] args) {
        RevistasForm form;
        try {
            form = new RevistasForm();
            form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
