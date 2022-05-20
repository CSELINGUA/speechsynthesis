

package com.example.pdfreader.utils

import androidx.appcompat.app.AppCompatActivity
import com.example.pdfreader.models.Model1
import com.example.pdfreader.models.TTSModel
import java.util.ArrayList


class DataSource: AppCompatActivity() {
    companion object {

        fun loadDummyData(): ArrayList<Model1> {
            var items = ArrayList<Model1>()
            items.add(Model1(null, "name", 12, 12, 12))
            items.add(Model1(null, "name", 12, 12, 12))
            items.add(Model1(null, "name", 12, 12, 12))
            items.add(Model1(null, "name", 12, 12, 12))

            return items
        }
            /*
            items.add(Model1("James.pdf", "very sweet and interesting"))
            items.add(Model1("Oiza.pdf", "very sweet and interesting"))
            items.add(Model1("Matthew.pdf", "very sweet and interesting"))
            items.add(Model1("Cassava.pdf", "very sweet and interesting"))
            items.add(Model1("Olive.pdf", "very sweet and interesting"))
            items.add(Model1("Banana.pdf", "very sweet and interesting"))
            items.add(Model1("Apple.pdf", "very sweet and interesting"))
            items.add(Model1("Water Melon.pdf", "very sweet and interesting"))
            items.add(Model1("Maroon.pdf", "very sweet and interesting"))
            items.add(Model1("Red.pdf", "very sweet and interesting"))
            items.add(Model1("Orange.pdf", "very sweet and interesting"))
            items.add(Model1("Orange.pdf", "very sweet and interesting"))
            items.add(Model1("Orange.pdf", "very sweet and interesting"))
            items.add(Model1("Mango.pdf", "very sweet and interesting"))
            items.add(Model1("James.pdf", "very sweet and interesting"))
            items.add(Model1("Oiza.pdf", "very sweet and interesting"))
            items.add(Model1("Matthew.pdf", "very sweet and interesting"))
            items.add(Model1("Cassava.pdf", "very sweet and interesting"))
            items.add(Model1("Olive.pdf", "very sweet and interesting"))
            items.add(Model1("Banana.pdf", "very sweet and interesting"))
            items.add(Model1("Apple.pdf", "very sweet and interesting"))
            items.add(Model1("Water Melon.pdf", "very sweet and interesting"))
            items.add(Model1("Maroon.pdf", "very sweet and interesting"))
            items.add(Model1("Red.pdf", "very sweet and interesting"))
            items.add(Model1("Orange.pdf", "very sweet and interesting"))
            items.add(Model1("Orange.pdf", "very sweet and interesting"))
            items.add(Model1("Orange.pdf", "very sweet and interesting"))
            items.add(Model1("Mango.pdf", "very sweet and interesting"))
            items.add(Model1("James.pdf", "very sweet and interesting"))
            items.add(Model1("Oiza.pdf", "very sweet and interesting"))
            items.add(Model1("Matthew.pdf", "very sweet and interesting"))
            items.add(Model1("Cassava.pdf", "very sweet and interesting"))
            items.add(Model1("Olive.pdf", "very sweet and interesting"))
            items.add(Model1("Banana.pdf", "very sweet and interesting"))
            items.add(Model1("Apple.pdf", "very sweet and interesting"))
            items.add(Model1("Water Melon.pdf", "very sweet and interesting"))
            items.add(Model1("Maroon.pdf", "very sweet and interesting"))
            items.add(Model1("Red.pdf", "very sweet and interesting"))
            items.add(Model1("Orange.pdf", "very sweet and interesting"))
            items.add(Model1("Orange.pdf", "very sweet and interesting"))




            return items
        }
        */

        fun loadDummyDataTTS(): ArrayList<TTSModel> {
            var items = ArrayList<TTSModel>()
            items.add(TTSModel("This study examined water supply and sanitation practices of students in selected halls of residence in Obafemi Awolowo University, Ile-Ife, Nigeria. "))
            items.add(TTSModel("It identified and examined the socio-economic characteristics of the students in the study area; examined the level of water supply provision and the state of sanitation facilities; established the relationship between respondents’ access to water supply and their sanitation practices; and also examine the factors which influence sanitation practices of students in the study area."))
            items.add(TTSModel("Of the 8 undergraduate halls of residence in the study area, 2 male and 2 female halls or residence was selected at random. "))
            items.add(TTSModel("This resulted in the selection of Adekunle Fajuyi and Angola halls of residence as samples of the male halls as well as Samuel Ladoke Akintola and Mozambique halls of residence the female hostels as female halls samples. "))
            items.add(TTSModel("There are, in total, 664 rooms in the 4 selected halls of residence from which 20% was randomly selected. "))
            items.add(TTSModel("In selecting the rooms to be sampled, the first room selection was done at random and subsequently, every 5th room was selected systematically. "))
            items.add(TTSModel("Questionnaire was administered on one occupant per room. "))
            items.add(TTSModel("The respondent was either the room representative or the occupant of the bed space number 1. As such, a total of 132 students were selected for the survey; of which 75 and 57 were male and female respondents respectively."))
            items.add(TTSModel("The study revealed that the majority of the respondents were below 25 years of age. Also, majority were in 100 level. "))
            items.add(TTSModel("Analysis of result showed that majority of the students sampled earned below 30000 per month. "))
            items.add(TTSModel("Findings revealed that many of the sampled rooms have 4-6 occupants. "))
            items.add(TTSModel("The respondents attest to the availability and functionality of various toilet and bathroom fixtures like water closet system, wash hand basin, tap, shower and urinals. "))
            items.add(TTSModel("The study found that majority of the rooms sampled share sanitation facilities with more than 2 rooms. "))
            items.add(TTSModel("Majority of the respondents agreed they received water supply between 18 and 24 hours per day, and majority of the respondents also agreed that the water supply service is reliable. "))
            items.add(TTSModel("The study findings revealed that majority of the respondents do not consume up to the minimum 50 litres of water requirement per day. "))
            items.add(TTSModel("The study found that female respondents are more inclined to proper sanitation practices than the males. "))
            items.add(TTSModel("The study found that the most practiced alternative to using the sanitation facilities is urinating outside. "))
            items.add(TTSModel("The study established that there is no significant correlation relationship between the quantity of water consumed and their sanitation practices. "))
            items.add(TTSModel("The study also identified factors influencing sanitation of which majority of the respondents identified poor awareness/education. "))
            items.add(TTSModel("Finally, the study concluded that the majority of students in the study area consumed below the minimum quantity of water per day; this is not however due to inadequacy in water supply service as majority of respondents claimed the service is reliable. "))
            items.add(TTSModel("The insanitary environment and practices also would not be traced entirely to the issue of water supply but largely on unsatisfactory state of the sanitation facilities, students’ attitude and non-existent or poor enforcement of environmental/sanitation regulation."))

            return items;
        }


    }


}

