package fbox;

import com.midtrans.Midtrans;
import com.midtrans.httpclient.CoreApi;
import com.midtrans.httpclient.TransactionApi;
import com.midtrans.httpclient.error.MidtransError;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ukunc
 */
public final class Payment extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    private boolean statusPasswordInput = false;
    public int userID;
    private static Image qris;
    private static String uuid;

    /**
     * Creates new form Payment_GUI
     *
     * @param userID
     * @throws com.midtrans.httpclient.error.MidtransError
     * @throws org.json.JSONException
     * @throws java.net.URISyntaxException
     * @throws java.net.MalformedURLException
     */
    public Payment(int userID) throws MidtransError, JSONException, URISyntaxException, MalformedURLException, IOException {
        Midtrans.serverKey = "SB-Mid-server-I7b-07mMFrRx3WX3cEe-ztW9";
        Midtrans.isProduction = false;

//        Midtrans.serverKey = "Mid-server-cy-ei-IruzSytCaGso01sVP5";
//        Midtrans.isProduction = true;
        this.userID = userID;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBackgroundImage(); // Memanggil method untuk mengatur gambar latar belakang
        initComponents();
        midtrans();
        qris = toBufferedImage(qris).getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(qris);
        jQris.setIcon(icon);
    }

    private static void midtrans() throws MidtransError, JSONException, URISyntaxException, MalformedURLException, IOException {
        UUID idRand = UUID.randomUUID();
        Map<String, Object> chargeParams = new HashMap<>();

        Map<String, String> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", idRand.toString());
        transactionDetails.put("gross_amount", "1");

        chargeParams.put("transaction_details", transactionDetails);
        chargeParams.put("payment_type", "gopay");

        JSONObject result = CoreApi.chargeTransaction(chargeParams);
        System.out.println(result);

        uuid = result.getString("order_id");
        JSONArray array = result.getJSONArray("actions");
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String name = obj.getString("name");
            String url = obj.getString("url");
            if (name.equals("generate-qr-code")) {
                URL qrisUrl = new URI(url).toURL();
                qris = ImageIO.read(qrisUrl);
            }
            if (name.equals("deeplink-redirect")) {
                System.out.println("For Testing : " + obj.getString("url"));
            }
        }
    }

    private static Boolean checkMidtrans() throws MidtransError, JSONException, URISyntaxException, MalformedURLException, IOException {
        System.out.println(uuid);
        JSONObject result = TransactionApi.checkTransaction(uuid);

        String transactionStatus = result.getString("transaction_status");
        System.out.println(result);

        return transactionStatus.equals("settlement");
    }

    private static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage bufferedImage) {
            return bufferedImage;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public void setBackgroundImage() {
        // Buat ImageIcon dari gambar GIF
        ImageIcon gifIcon = new ImageIcon(getClass().getResource("/icon/phomoriaIcon/fe.jpg"));

        // Dapatkan gambar dari ImageIcon
        Image gifImage = gifIcon.getImage();

        // Set latar belakang JFrame dengan gambar GIF
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(gifImage, 0, 0, getWidth(), getHeight(), this);
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header = new javax.swing.JPanel();
        iconClose = new javax.swing.JPanel();
        icClose = new javax.swing.JLabel();
        iconMax = new javax.swing.JPanel();
        icMax = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        bt_back = new javax.swing.JLabel();
        bt_back1 = new javax.swing.JLabel();
        jQris = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        header.setBackground(new java.awt.Color(0, 0, 0));
        header.setPreferredSize(new java.awt.Dimension(800, 50));

        iconClose.setBackground(new java.awt.Color(0, 0, 0));
        iconClose.setPreferredSize(new java.awt.Dimension(50, 50));
        iconClose.setLayout(new java.awt.BorderLayout());

        icClose.setBackground(new java.awt.Color(51, 102, 255));
        icClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/delete_32px.png"))); // NOI18N
        icClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        icClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                icCloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                icCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                icCloseMouseExited(evt);
            }
        });
        iconClose.add(icClose, java.awt.BorderLayout.CENTER);

        iconMax.setBackground(new java.awt.Color(0, 0, 0));
        iconMax.setPreferredSize(new java.awt.Dimension(50, 50));
        iconMax.setLayout(new java.awt.BorderLayout());

        icMax.setBackground(new java.awt.Color(51, 102, 255));
        icMax.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icMax.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/full_screen_32px.png"))); // NOI18N
        icMax.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                icMaxMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                icMaxMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                icMaxMouseExited(evt);
            }
        });
        iconMax.add(icMax, java.awt.BorderLayout.CENTER);

        jLabel1.setFont(new java.awt.Font("Circular Std Black", 0, 64)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/PHOMORIA21.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Circular Std Black", 0, 30)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Phomoria");

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(29, 29, 29)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 892, Short.MAX_VALUE)
                .addComponent(iconMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(iconClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, headerLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(iconMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        bt_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/phomoriaIcon/back button.png"))); // NOI18N
        bt_back.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_backMouseClicked(evt);
            }
        });
        jPanel1.add(bt_back, new java.awt.GridBagConstraints());

        bt_back1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/phomoriaIcon/back button.png"))); // NOI18N
        bt_back1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bt_back1MouseClicked(evt);
            }
        });
        jPanel1.add(bt_back1, new java.awt.GridBagConstraints());

        jQris.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, 1211, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1199, Short.MAX_VALUE)
                    .addComponent(jQris, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(187, 187, 187)
                .addComponent(jQris, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1211, 852));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
public void changeColor(JPanel hover, Color rand) {
        hover.setBackground(rand);

    }
    private void icCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icCloseMouseClicked
        if (!statusPasswordInput) {
            // Jika statusPasswordInput false, tampilkan input password
            showPasswordClose();
        }
    }//GEN-LAST:event_icCloseMouseClicked

    private void icCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icCloseMouseEntered
        changeColor(iconClose, new Color(240, 240, 240));
    }//GEN-LAST:event_icCloseMouseEntered

    private void icCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icCloseMouseExited
        changeColor(iconClose, new Color(0, 0, 0));
    }//GEN-LAST:event_icCloseMouseExited

    private void icMaxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icMaxMouseClicked
        if (!statusPasswordInput) {
            showPasswordInput();
        }
    }//GEN-LAST:event_icMaxMouseClicked
    private void showPasswordInput() {
        JPasswordField passwordField = new JPasswordField(20);

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Masukkan password:"));
        passwordPanel.add(passwordField);

        // Menampilkan panel dan meminta input password
        int result = JOptionPane.showConfirmDialog(null, passwordPanel, "Konfirmasi Password", JOptionPane.OK_CANCEL_OPTION);
        statusPasswordInput = true; // Set statusPasswordInput menjadi true ketika password input aktif

        if (result == JOptionPane.OK_OPTION) {
            // Gantilah "passwordAnda" dengan password yang benar
            String passwordBenar = "admin";
            char[] inputPassword = passwordField.getPassword();

            if (new String(inputPassword).equals(passwordBenar)) {
                if (getExtendedState() != fbox.Main.MAXIMIZED_BOTH) {
                    setExtendedState(Main.MAXIMIZED_BOTH);
                } else {
                    setExtendedState(Main.NORMAL);
                }
                statusPasswordInput = false;
            } else {
                JOptionPane.showMessageDialog(null, "Password salah!", "Error", JOptionPane.ERROR_MESSAGE);
                statusPasswordInput = false;
            }
        } else if (result == JOptionPane.CANCEL_OPTION) {
            statusPasswordInput = false;
        }
    }

    private void showPasswordClose() {
        // Membuat JTextField untuk memasukkan password
        JPasswordField passwordField = new JPasswordField(20);

        // Membuat panel untuk menampilkan JTextField
        JPanel passwordPanel = new JPanel();
        passwordPanel.add(new JLabel("Masukkan password:"));
        passwordPanel.add(passwordField);

        // Menampilkan panel dan meminta input password
        int result = JOptionPane.showConfirmDialog(null, passwordPanel, "Konfirmasi Password", JOptionPane.OK_CANCEL_OPTION);
        statusPasswordInput = true; // Set statusPasswordInput menjadi true ketika password input aktif

        if (result == JOptionPane.OK_OPTION) {
            // Gantilah "passwordAnda" dengan password yang benar
            String passwordBenar = "admin";
            char[] inputPassword = passwordField.getPassword();

            // Periksa apakah password yang dimasukkan benar
            if (new String(inputPassword).equals(passwordBenar)) {
                // Jika password benar, ubah ukuran jendela

                statusPasswordInput = false;
                System.exit(0);
            } else {
                // Jika password salah, tampilkan pesan kesalahan
                JOptionPane.showMessageDialog(null, "Password salah!", "Error", JOptionPane.ERROR_MESSAGE);
                statusPasswordInput = false;
            }
        } else if (result == JOptionPane.CANCEL_OPTION) {
            statusPasswordInput = false; // Atur statusPasswordInput menjadi false
            // Tutup dialog
        }
    }
    private void icMaxMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icMaxMouseEntered
        changeColor(iconMax, new Color(240, 240, 240));
    }//GEN-LAST:event_icMaxMouseEntered

    private void icMaxMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_icMaxMouseExited
        changeColor(iconMax, new Color(0, 0, 0));
    }//GEN-LAST:event_icMaxMouseExited

    private void bt_backMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_backMouseClicked
        try {
            dispose();
            System.out.println("dispose berhasil dijalankan");
            Dashboard mainpage = new Dashboard(this.userID);
            mainpage.show();
            System.out.println("pinda halaman");
        } catch (Exception e) {
            System.err.println("1. dispose gagal dijalankan: " + e.getMessage());
        }
    }//GEN-LAST:event_bt_backMouseClicked

    private void bt_back1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bt_back1MouseClicked
        try {
            if (!uuid.isEmpty()) {
                if (checkMidtrans()) {
                    System.out.println("SUCCESS");
                    dispose();
                    System.out.println("dispose berhasil dijalankan");
                    Main mainpage = new Main(this.userID);
                    mainpage.show();
                    System.out.println("pinda halaman");
                } else {
                    JOptionPane.showMessageDialog(null, "Gagal melakukan pembayaran, coba lagi", "Warning", JOptionPane.WARNING_MESSAGE);
//                    midtrans();
//                    qris = toBufferedImage(qris).getScaledInstance(400, 400, Image.SCALE_SMOOTH);
//                    ImageIcon icon = new ImageIcon(qris);
//                    jQris.setIcon(icon);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Bulum melakukan pembayaran", "Warning", JOptionPane.WARNING_MESSAGE);
//                midtrans();
//                qris = toBufferedImage(qris).getScaledInstance(400, 400, Image.SCALE_SMOOTH);
//                ImageIcon icon = new ImageIcon(qris);
//                jQris.setIcon(icon);
            }
        } catch (MidtransError | IOException | URISyntaxException | JSONException e) {
            System.err.println("1. dispose gagal dijalankan: " + e.getMessage());
        }
    }//GEN-LAST:event_bt_back1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Payment(0).setVisible(true);
            } catch (MidtransError | JSONException | URISyntaxException | MalformedURLException ex) {
                Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bt_back;
    private javax.swing.JLabel bt_back1;
    private javax.swing.JPanel header;
    private javax.swing.JLabel icClose;
    private javax.swing.JLabel icMax;
    private javax.swing.JPanel iconClose;
    private javax.swing.JPanel iconMax;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel jQris;
    // End of variables declaration//GEN-END:variables
}
