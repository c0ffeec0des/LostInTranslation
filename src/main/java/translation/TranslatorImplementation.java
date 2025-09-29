package translation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslatorImplementation implements Translator{

    /*private Map<String, String> countryCodeToCountry = new HashMap<>();
    private Map<String, String> countryToCountryCode = new HashMap<>();

    CountryCodeConverter CCconverter = new CountryCodeConverter();
    // converts country <--> country code
    LanguageCodeConverter LCconverter = new LanguageCodeConverter();
    // converts language <--> language code
    */

    JSONTranslator jsonTranslator = new JSONTranslator();
    // converts (country code, language) --> (expected name in language)

    @Override
    public List<String> getCountryCodes() {
        return jsonTranslator.getCountryCodes();
    }

    @Override
    public List<String> getLanguageCodes() {
        return jsonTranslator.getLanguageCodes();
    }

    @Override
    public String translate(String countryCode, String languageCode){

        return jsonTranslator.translate(countryCode, languageCode);
    }

}
