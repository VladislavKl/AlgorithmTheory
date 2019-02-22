#include <iostream>
#include <string>
#include <regex>

int main() {

    std::string str = "На вершине Уральского нагорья пришла зима кек ну и туh невзначай макс.    температура   побережья Австралии в БГУ -500°   jg    hkfju tseгЛ ШШАГЕ осврчояоешпНЕВШЛ РМ ЩРА Нj";
    std::cmatch result;
    std::regex regular(
            "(([0-9A-Za-zА-Яа-яёЁ]){1,}\\s{1,}){0,}(((В|в)есна)|((Л|л)ето)|((о|О)сень)|((з|З)има))(\\s){1,}(([0-9A-Za-zА-Яа-я]){1,}(\\s){1,}){0,}(((М|м)аксимальная)|((м|М)акс.)|средняя|((с|С)редняя))(\\s){1,}температура(\\s){1,}(([0-9A-Za-zА-Яа-яёЁ]){1,}\\s{1,}){0,}((-){0,1}[1-9]{0,1}[0-9])(\\s){0,}(°|градусов|degrees)[a-zA-Zа-яА-ЯёЁ\\s]{0,}");

    std::regex wrongTemperaturRegular(
            "(([0-9A-Za-zА-Яа-яёЁ]){1,}\\s{1,}){0,}(((В|в)есна)|((Л|л)ето)|((о|О)сень)|((з|З)има))(\\s){1,}(([0-9A-Za-zА-Яа-я]){1,}(\\s){1,}){0,}(((М|м)аксимальная)|((м|М)акс.)|средняя|((с|С)редняя))(\\s){1,}температура(\\s){1,}(([0-9A-Za-zА-Яа-яёЁ]){1,}\\s{1,}){0,}(((-){0,1}[0-9]{3,})|((-){0,1}0[0-9]{1,}))(\\s){0,}(°|градусов|degrees)[a-zA-Zа-яА-ЯёЁ\\s]{0,}");

    if (std::regex_match(str.c_str(), result, regular)) {
        std::cout << "Вся строка удовлетворяет условию" << std::endl;
    } else if (std::regex_match(str.c_str(), result, wrongTemperaturRegular)) {
        std::cout << "Температура задана неправильно!!!" << std::endl;
    } else
        std::cout << "Строка не удовлетворяет условию" << std::endl;



    ///////////////////////////////////подстрока////////////////////////////////////////////



    std::string s = "12юбб,ббжь,Ю,ЮБЮ.юб.На вершине Уральского нагорья пришла зима кек ну и туh невзначай макс.    температура   побережья Австралии в БГУ -50°   jg    hkfju tseгЛ ШШАГЕ осврчояоешпНю.ц,мЕВШЛ РМ ЩРА Нj asw1 235 gsd dfsga";
    std::smatch m;
    std::regex e(
            "(([0-9A-Za-zА-Яа-яёЁ]){1,}\\s{1,}){0,}(((В|в)есна)|((Л|л)ето)|((о|О)сень)|((з|З)има))(\\s){1,}(([0-9A-Za-zА-Яа-я]){1,}(\\s){1,}){0,}(((М|м)аксимальная)|((м|М)акс.)|средняя|((с|С)редняя))(\\s){1,}температура(\\s){1,}(([0-9A-Za-zА-Яа-яёЁ]){1,}\\s{1,}){0,}((-){0,1}[1-9]{0,1}[0-9])(\\s){0,}(°|градусов|degrees)[a-zA-Zа-яА-ЯёЁ\\s]{0,}");

    if (std::regex_search(s, m, e)) {
        std::cout << m[0] << "\n";
    }





    //////////////////////////////////////задание 3//////////////////////////////////////////////

    //std::cout<< "Enter your string for regular expression to search" << std::endl;
    std::string line = "this subject has a submarine as a subsequence";
    //getline(std::cin, line);
    std::string string(line);
    std::smatch match;
    //std::cout<< "Enter your regular expression" << std::endl;
    line = "\\b(sub)([^ ]*)";
    //getline(std::cin, line);
    std::regex reg(line);

    std::cout << "Target sequence: " << string << std::endl;
    std::cout << "The following matches and submatches were found:" << std::endl;

    std::string temporate;
    while (std::regex_search(string, match, reg)) {
        for (auto x :match) {
            if (x.length() > temporate.length())
                temporate = x;
        }
        string = match.suffix().str();
    }
    std::cout << temporate << std::endl;


    return 0;
}