package translation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GUI extends JFrame {

    private final JSONTranslator translator;
    private final CountryCodeConverter countryConverter;
    private final LanguageCodeConverter languageConverter;

    private final JComboBox<String> languageDropdown;
    private final JList<String> countryList;
    private final JLabel translationLabel;

    private String[] languageDisplayNames;
    private String[] languageCodes;
    private String[] countryDisplayNames;
    private String[] countryCodes;

    public GUI() throws Exception {
        super("Country Name Translator");

        translator = new JSONTranslator("sample.json");
        countryConverter = new CountryCodeConverter("country-codes.txt");
        languageConverter = new LanguageCodeConverter("language-codes.txt");

        // --- Load languages ---
        List<String> langCodesRaw = translator.getLanguageCodes();
        List<NC> langPairs = new ArrayList<>();
        for (String code : langCodesRaw) {
            String name = languageConverter.fromLanguageCode(code);
            if (name == null) name = code;
            langPairs.add(new NC(name, code));
        }
        langPairs.sort(Comparator.comparing(nc -> nc.name.toLowerCase()));
        languageDisplayNames = new String[langPairs.size()];
        languageCodes = new String[langPairs.size()];
        for (int i = 0; i < langPairs.size(); i++) {
            languageDisplayNames[i] = langPairs.get(i).name;
            languageCodes[i] = langPairs.get(i).code;
        }

        // --- Load countries ---
        List<String> ctryCodesRaw = translator.getCountryCodes();
        List<NC> ctryPairs = new ArrayList<>();
        for (String code : ctryCodesRaw) {
            String name = countryConverter.fromCountryCode(code);
            if (name == null) name = code.toUpperCase();
            ctryPairs.add(new NC(name, code));
        }
        ctryPairs.sort(Comparator.comparing(nc -> nc.name.toLowerCase()));
        countryDisplayNames = new String[ctryPairs.size()];
        countryCodes = new String[ctryPairs.size()];
        for (int i = 0; i < ctryPairs.size(); i++) {
            countryDisplayNames[i] = ctryPairs.get(i).name;
            countryCodes[i] = ctryPairs.get(i).code;
        }

        // --- Layout ---
        setLayout(new BorderLayout(8, 8));

        // Top panel: Language
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Language:"));
        languageDropdown = new JComboBox<>(languageDisplayNames);
        top.add(languageDropdown);
        add(top, BorderLayout.NORTH);

        // Center panel: Translation label + country list
        JPanel center = new JPanel(new BorderLayout());
        translationLabel = new JLabel("Translation:", SwingConstants.LEFT);
        translationLabel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        center.add(translationLabel, BorderLayout.NORTH);

        countryList = new JList<>(countryDisplayNames);
        countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(countryList);
        center.add(scrollPane, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);

        // --- Events ---
        languageDropdown.addActionListener(e -> updateTranslation());
        countryList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) updateTranslation();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateTranslation() {
        int langIdx = languageDropdown.getSelectedIndex();
        int ctryIdx = countryList.getSelectedIndex();

        if (langIdx < 0 || ctryIdx < 0) {
            translationLabel.setText("Translation:");
            return;
        }

        String langCode = languageCodes[langIdx];
        String ctryCode = countryCodes[ctryIdx];
        String countryName = countryDisplayNames[ctryIdx];

        String result = translator.translate(ctryCode, langCode);
        if (result != null) {
            translationLabel.setText("Translation: " + result);
        } else {
            translationLabel.setText("Translation: (not found)");
        }
    }

    private static class NC {
        final String name;
        final String code;
        NC(String n, String c) { name = n; code = c; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new GUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
