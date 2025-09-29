package translation;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
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
            JTextField countryField = new JTextField(10);
            countryPanel.add(new JLabel("Country:"));
            countryField.setText("afg");

            // Combo box
            JComboBox<String> countryComboBox = new JComboBox<>();
            for(String countryCode : avail_country_codes) {
                countryComboBox.addItem(countryCode);
            }
            countryPanel.add(countryComboBox);
            countryComboBox.addItemListener(new ItemListener() {

                /**
                 * Invoked when an item has been selected or deselected by the user.
                 * The code written for this method performs the operations
                 * that need to occur when an item is selected (or deselected).
                 *
                 * @param e the event to be processed
                 */
                @Override
                public void itemStateChanged(ItemEvent e) {

                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        String country = countryComboBox.getSelectedItem().toString();
                        countryField.setText(country);
                    }
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

                /**
                 * Invoked when an item has been selected or deselected by the user.
                 * The code written for this method performs the operations
                 * that need to occur when an item is selected (or deselected).
                 *
                 * @param e the event to be processed
                 */
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
