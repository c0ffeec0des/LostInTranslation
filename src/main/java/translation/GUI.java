package translation;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;


public class GUI {

    public static void main(String[] args) {

        TranslatorImplementation translator = new TranslatorImplementation();
        // converts (country code, language code) --> expected country name in target language
        List<String> avail_country_codes = translator.getCountryCodes();
        List<String> avail_language_codes = translator.getLanguageCodes();

        CountryCodeConverter CCconverter = new CountryCodeConverter();
        // converts country <--> country code
        LanguageCodeConverter LCconverter = new LanguageCodeConverter();
        // converts language <--> language code

        SwingUtilities.invokeLater(() -> {
            // COUNTRY PANEL
            JPanel countryPanel = new JPanel();
            countryPanel.setLayout(new GridLayout(0, 2));
            countryPanel.add(new JLabel("Country:"), 0);
            JTextField countryField = new JTextField(10);



            String[] items = new String[translator.getCountryCodes().size()];

            JComboBox<String> countryComboBox = new JComboBox<>();
            int i = 0;
            for(String countryCode : translator.getCountryCodes()) {
                items[i++] = countryCode;
            }

            JList<String> list = new JList<>(items);
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(list);
            countryPanel.add(scrollPane, 1);

            list.addListSelectionListener(new ListSelectionListener() {

                /**
                 * Called whenever the value of the selection changes.
                 *
                 * @param e the event that characterizes the change.
                 */
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String country = list.getSelectedValue();
                    System.out.println(country);
                    countryField.setText(country);
                }
            });


            // LANGUAGE PANEL
            JPanel languagePanel = new JPanel();
            JTextField languageField = new JTextField(10);
            languagePanel.add(new JLabel("Language:"));
            languageField.setText("de");

            // Combo box
            JComboBox<String> languageComboBox = new JComboBox<>();
            for(String languageCode : avail_language_codes) {
                languageComboBox.addItem(languageCode);
            }
            languagePanel.add(languageComboBox);
            languageComboBox.addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {

                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String language = languageComboBox.getSelectedItem().toString();
                        languageField.setText(language);
                    }
                }

            });


            JPanel buttonPanel = new JPanel();
            JButton submit = new JButton("Submit");
            buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String language = languageField.getText();
                    String country = countryField.getText();

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.

                    String result = translator.translate(country, language);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(countryPanel);
            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
