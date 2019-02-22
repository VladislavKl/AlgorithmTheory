#include <iostream>
#include <string>
#include <regex>

int main() {

    std::string str = "Ученые из Британии";
    std::cmatch result;
    std::regex regular("[У|у]ченые{0,}\\s{0,}из{0,}\\s{0,}[Б|б]ритании{0,}");

    if (std::regex_match(str.c_str(), result, regular))
        for (int i = 0; i < result.size(); ++i)
            std::cout << result[i] << std::endl;
    return 0;
}