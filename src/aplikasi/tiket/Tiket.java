/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi.tiket;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author Har
 */
public class Tiket extends javax.swing.JFrame {

    /**
     * Creates new form Tiket
     */
    
    public void load() {
        DefaultTableModel model = (DefaultTableModel) tabel.getModel();
        model.getDataVector().removeAllElements();

        try {
            Connection c = dbms.getkoneksi();
            Statement s = c.createStatement();
            String sql = "SELECT * FROM `tiket`";
            ResultSet r = s.executeQuery(sql);

            while (r.next()) {
                Object[] o = new Object[9];
                o[0] = r.getString("nama");  
                o[1] = r.getString("nik");  
                o[2] = r.getString("no_telp");
                o[3] = r.getString("tanggal");
                o[4] = r.getString("jenis_tiket");
                o[5] = r.getString("kelas_tiket");
                o[6] = r.getString("asal");
                o[7] = r.getString("tujuan");
                o[8] = r.getString("kursi");
                model.addRow(o);
            }
            r.close();
            s.close();
        } catch (SQLException e) {
            System.out.println("Terjadi kesalahan" + e);
        }
    }
    
    public Tiket() {
        initComponents();
        setTitle("Pembuat Tiket");
        load();
    }
    
    public void tambah(String nama, String nik, String telp, String tanggal,String jenis,String kelas,String asal,String tujuan,String kursi){
        try {
            Connection c = dbms.getkoneksi();
            Statement stat = c.createStatement();
            try {
                String sql = "INSERT INTO `tiket`( `nama`, `nik`, `no_telp`, `tanggal`, `jenis_tiket`, `kelas_tiket`, `asal`, `tujuan`, `kursi`) VALUES ('"+nama+"','"+nik+"','"+telp+"','"+tanggal+"','"+jenis+"','"+kelas+"','"+asal+"','"+tujuan+"','"+kursi+"');";
                stat.execute(sql);
                stat.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        } catch (SQLException ex) {
            System.out.println("GAGAL MENAMBAHKAN DATA");
        }
        load();
    }
    
    public void tambahAsal(String asal){
        this.asal.addItem(asal);
    }
    
    public void tambahTujuan(String tujuan){
        this.tujuan.addItem(tujuan);
    }
    
    public static String acak (int n){
        String daftar = "0123456789";
        
        StringBuilder sb = new StringBuilder(n);
        
        for (int i=0;i<n;i++){
            int index = (int)(daftar.length()*Math.random());
            sb.append(daftar.charAt(index));
        }
        
        return sb.toString();
    }
    
    public void tambahKursi (){
        kursi.removeAllItems();
        String[] semuaKursi = new String[]{
            "A1","A2","A3","A4","A5",
            "B1","B2","B3","B4","B5",
            "C1","C2","C3","C4","C5",
            "D1","D2","D3","D4","D5",
            "E1","E2","E3","E4","E5"
        };
        for (int h=0;h<semuaKursi.length;h++){
            kursi.addItem(semuaKursi[h]);
        }
    }
    
    public void hapusKursi(String jenis){
        DefaultTableModel model = (DefaultTableModel)tabel.getModel();
        int kolom = tabel.getRowCount();
        String[] semuaKursi = new String[]{
            "A1","A2","A3","A4","A5",
            "B1","B2","B3","B4","B5",
            "C1","C2","C3","C4","C5",
            "D1","D2","D3","D4","D5",
            "E1","E2","E3","E4","E5"
        };  
        String[][] listedKursi = new String[kolom][2];

        
        for(int i=0;i<kolom;i++){
        String kursis = model.getValueAt(i, 8).toString();
        String kendaraan = model.getValueAt(i, 4).toString();
        
        listedKursi[i][0]=kendaraan;
        listedKursi[i][1]=kursis;
        
        }
        
        for(int j=0;j<semuaKursi.length;j++){
            for(int k=0;k<listedKursi.length;k++){
                if (listedKursi[k][0].equals(jenis)){
                    if (!listedKursi[k][1].equals(semuaKursi[j])){
                        kursi.removeItem(listedKursi[k][1]);
                    }
                }
            }
        }
    }
    
    public void buatTiket(String jenis){
        //siapkan data untuk tiket
        String valueTiket = tiket.getSelectedItem().toString();
        String valueKelas = kelas.getSelectedItem().toString();
        String data_asal = asal.getSelectedItem().toString();
        String data_tujuan = this.tujuan.getSelectedItem().toString();
        String data_nama = this.nama.getText();
        String data_nik = this.nik.getText();
        String data_telp = this.telp.getText();
        String nomer_tiket = acak(12);
        Date date = tanggal.getDate();
        String data_tanggal = DateFormat.getDateInstance().format(date);
        String data_kursi = kursi.getSelectedItem().toString();

        
        //komponen font
        Font font = new Font("Futura Md BT",Font.TRUETYPE_FONT,30);

    
        File pesawat = new File("D:\\tiket\\"+jenis+".png");
        
            BufferedImage image = null;
            try {
                image = ImageIO.read(pesawat);
            } catch (IOException ex) {
                Logger.getLogger(Tiket.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            Graphics g = image.getGraphics();
            g.setColor(Color.BLACK);
            g.setFont(font);
            g.drawString(data_nama,59, 190);
            g.drawString(nomer_tiket,1124, 190);
            g.drawString(data_asal,59, 310);
            g.drawString(data_tujuan,59, 420);
            g.drawString(data_tanggal,650, 310);
            g.drawString(valueKelas,650, 420);
            g.drawString(data_kursi,1124, 420);
            g.dispose();
            try {
                ImageIO.write(image, "png", new File("D:\\Tiket "+jenis+" "+data_nama+" .png"));
            } catch (IOException ex) {
                Logger.getLogger(Tiket.class.getName()).log(Level.SEVERE, null, ex);
            }
                  tambah(data_nama, data_nik, data_telp, data_tanggal, valueTiket, valueKelas, data_asal, data_tujuan, data_kursi);

                  showMessageDialog(null, "Tiket "+jenis+" Dicetak");
        
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nama = new javax.swing.JTextField();
        tanggal = new com.toedter.calendar.JDateChooser();
        tiket = new javax.swing.JComboBox<>();
        kelas = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        tujuan = new javax.swing.JComboBox<>();
        buat = new javax.swing.JButton();
        asal = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        nik = new javax.swing.JTextField();
        kursi = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabel = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        telp = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();

        jLabel8.setText("jLabel8");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("Nama");

        jLabel3.setText("Tanggal");

        jLabel4.setText("Tiket");

        jLabel5.setText("Kelas");

        tiket.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pesawat", "Kereta", "Kapal", "Bis" }));
        tiket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tiketActionPerformed(evt);
            }
        });

        kelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ekonomi", "Bisnis" }));

        jLabel6.setText("Dari");

        jLabel1.setText("Ke");

        tujuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tujuanActionPerformed(evt);
            }
        });

        buat.setText("Buat");
        buat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buatActionPerformed(evt);
            }
        });

        jLabel7.setText("NIK");

        kursi.setToolTipText("");

        jLabel9.setText("Kursi");

        tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Nama", "NIK", "No Telp", "Tanggal","Jenis Tiket","Kelas Tiket","Asal","Tujuan","Kursi"
            }
        ));
        jScrollPane3.setViewportView(tabel);

        jLabel10.setText("No Telp");

        jLabel11.setText("History Pembuatan Tiket :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(kursi, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(asal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(13, 13, 13))
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tiket, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(kelas, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jLabel1))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(29, 29, 29)
                            .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(20, 20, 20)
                        .addComponent(telp, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nik, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(buat))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel11)
                        .addGap(0, 369, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(telp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tiket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tujuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(asal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kursi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buat)
                .addGap(19, 19, 19))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tujuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tujuanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tujuanActionPerformed

    private void tiketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tiketActionPerformed
        String valueTiket = tiket.getSelectedItem().toString();
        if (valueTiket == "Pesawat"){
            tujuan.removeAllItems();
            asal.removeAllItems();
            String[] valueAsal = {"Tangerang","Denpasar","Surabaya","Makassar","Medan","Yogyakarta","Balikpapan","Jakarta","Batam","Palembang"};
            String[] valueTujuan = {"Semarang","Bandung","Pontianak","Mataram","Pekanbaru","Padang","Banjarmasin","Manado","Jayapura","Kupang"};
            for(int i=0;i<valueAsal.length;i++){
                tambahAsal(valueAsal[i]);
                tambahTujuan(valueTujuan[i]);
            }
            tambahKursi();
            hapusKursi("Pesawat");
        }else if (valueTiket == "Kereta"){
            tujuan.removeAllItems();
            asal.removeAllItems();
            String[] valueAsal = {"Semarang","Jakarta","Bandung","Cirebon","Jember","Madiun","Surabaya","Mojokerto","Solo","Purwokerto"};
            String[] valueTujuan = {"Jakarta"," Bekasi","Blitar","Blora","Bojonegoro","Bogor","Bondowoso","Depok","Demak","Gresik"};
            for(int i=0;i<valueAsal.length;i++){
                tambahAsal(valueAsal[i]);
                tambahTujuan(valueTujuan[i]);
            }
            tambahKursi();
            hapusKursi("Kereta");
        }else if (valueTiket == "Kapal"){
            tujuan.removeAllItems();
            asal.removeAllItems();
            String[] valueAsal = {"Madura","Surabaya","Banyuwangi","Cirebon","Cilegon","Tulungagung","Jakarta","Semarang","Garut","Cilacap"};
            String[] valueTujuan = {"Batam","Lampung","Bengkulu","Aceh","Padang","Balikpapan","Samarinda","Bali","Makassar","Jayapura"};
            for(int i=0;i<valueAsal.length;i++){
                tambahAsal(valueAsal[i]);
                tambahTujuan(valueTujuan[i]);
            }
            tambahKursi();
            hapusKursi("Kapal");
        }else {
            tujuan.removeAllItems();
            asal.removeAllItems();
            String[] valueAsal = {"Mojokerto","Surabaya","Malang","Jombang","Batu","Sidoarjo","Gresik","Pasuruan","Kediri","Blitar"};
            String[] valueTujuan = {"Yogyakarta","Semarang","Solo","Bandung","Sukabumi","Sumedang","Jakarta","Bogor","Bekasi","Tasikmalaya"};
            for(int i=0;i<valueAsal.length;i++){
                tambahAsal(valueAsal[i]);
                tambahTujuan(valueTujuan[i]);
            }
         tambahKursi();
            hapusKursi("Bis");
        }      
    }//GEN-LAST:event_tiketActionPerformed

    private void buatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buatActionPerformed
        String valueTiket = tiket.getSelectedItem().toString();
        if ("Pesawat".equals(valueTiket)){
            buatTiket(valueTiket);
        }else if ("Kereta".equals(valueTiket)){
            buatTiket(valueTiket);
        }else if ("Kapal".equals(valueTiket)){
            buatTiket(valueTiket);
        }else {
            buatTiket(valueTiket);
        }
        
        tambahKursi();
        hapusKursi(valueTiket);
        
    }//GEN-LAST:event_buatActionPerformed

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
            java.util.logging.Logger.getLogger(Tiket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tiket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tiket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tiket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tiket().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> asal;
    private javax.swing.JButton buat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> kelas;
    private javax.swing.JComboBox<String> kursi;
    private javax.swing.JTextField nama;
    private javax.swing.JTextField nik;
    private javax.swing.JTable tabel;
    private com.toedter.calendar.JDateChooser tanggal;
    private javax.swing.JTextField telp;
    private javax.swing.JComboBox<String> tiket;
    private javax.swing.JComboBox<String> tujuan;
    // End of variables declaration//GEN-END:variables
}
