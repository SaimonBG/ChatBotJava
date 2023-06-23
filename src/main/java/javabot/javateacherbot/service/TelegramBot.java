package javabot.javateacherbot.service;

/**
 * Created by
 * Bogun Semen
 * Класс где лежит вся логика работы нашего телеграмм бота
 */

import javabot.javateacherbot.config.BotConfig;
import javabot.javateacherbot.model.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    //Конфигурация бота
    final BotConfig config;
    //Репозиторий
    @Autowired
    private AnswerRepository answerRepository;

    //Конструктор класса TelegramBot
    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    // TODO: Метод содержащий всю логику ответов нашего телеграмм бота
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            //Кладем в переменную наш вопрос для дальнейшей обработки в коде
            String messageText = update.getMessage().getText();
            //Кладем в переменную чайт айди, уникальный номер пользователя в телеграмм для дальнейшей обработки в коде
            long chatId = update.getMessage().getChatId();

                if (messageText.contains("/start")) {
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                } else if (messageText.toLowerCase().contains("привет".toLowerCase())){
                    answersOnQuestions(chatId, "Привет");
                } else if (messageText.toLowerCase().contains("как".toLowerCase()) && messageText.toLowerCase().contains("дел".toLowerCase())){
                    //Использую метод "random()" для случайного ответа бота на вопрос "как дела?"
                    if (Math.random() < 0.3){
                            answersOnQuestions(chatId, "Лучшим образом");
                        } else if (Math.random() > 0.3 && Math.random() < 0.5){
                            answersOnQuestions(chatId, "Просторно в мысленном пространстве");
                        } else if (Math.random() > 0.5 && Math.random() < 0.7){
                            answersOnQuestions(chatId, "Беспокойно и нервозно, неоднозначно, некоторые неопределенности");
                        }  else {
                            answersOnQuestions(chatId, "Сойдет");
                        }

                } else if (messageText.toLowerCase().contains("поговорим".toLowerCase()) || messageText.toLowerCase().contains("расскажи".toLowerCase())){
                    answersOnQuestions(chatId, "Хорошо давай");
                } else if (messageText.toLowerCase().contains("ООП".toLowerCase())){
                    //После получения вопроса ниже получаем данные из базы данных
                    answerRepository.findById(Long.valueOf(1)).ifPresent(answer -> answersOnQuestions(chatId, answer.getTheme() + ":"));
                    answerRepository.findById(Long.valueOf(1)).ifPresent(answer -> answersOnQuestions(chatId, answer.getAnswer()));
                    answerRepository.findById(Long.valueOf(7)).ifPresent(answer -> answersOnQuestions(chatId, answer.getTheme() + ":"));
                    answerRepository.findById(Long.valueOf(7)).ifPresent(answer -> answersOnQuestions(chatId, answer.getAnswer()));
                    //Добавлена ссылка где лежит полная, подробная информация в источнике
                    answersOnQuestions(chatId, "Более подробно почитай тут:\n https://habr.com/ru/companies/otus/articles/525336/");
                } else if (messageText.toLowerCase().contains("SOLID".toLowerCase())){
                    //Здесь сделано наборот в сравнении с предыдущим вариантом, получаем ссылку с подробной информацией из базы данных, а ответ из кода
                    answerRepository.findById(Long.valueOf(5)).ifPresent(answer -> answersOnQuestions(chatId, answer.getTheme() + ":"));
                    answersOnQuestions(chatId, "SOLID - это принципы разработки программного обеспечения, следуя которым Вы получите хороший код, " +
                            "который в дальнейшем будет хорошо масштабироваться и поддерживаться в рабочем состоянии. Более подробно прочитай перейдя по ссылке ниже.");
                    answerRepository.findById(Long.valueOf(5)).ifPresent(answer -> answersOnQuestions(chatId, answer.getAnswer()));
                } else if (messageText.toLowerCase().contains("типы".toLowerCase())){
                    answerRepository.findById(Long.valueOf(2)).ifPresent(answer -> answersOnQuestions(chatId, answer.getTheme() + ":"));
                    answerRepository.findById(Long.valueOf(2)).ifPresent(answer -> answersOnQuestions(chatId, answer.getAnswer()));
                } else if (messageText.toLowerCase().contains("класс".toLowerCase()) || messageText.toLowerCase().contains("Object".toLowerCase())){
                    answerRepository.findById(Long.valueOf(3)).ifPresent(answer -> answersOnQuestions(chatId, answer.getTheme() + ":"));
                    answerRepository.findById(Long.valueOf(3)).ifPresent(answer -> answersOnQuestions(chatId, answer.getAnswer()));
                } else if (messageText.toLowerCase().contains("примитив".toLowerCase())){
                    answerRepository.findById(Long.valueOf(4)).ifPresent(answer -> answersOnQuestions(chatId, answer.getTheme() + ":"));
                    answerRepository.findById(Long.valueOf(4)).ifPresent(answer -> answersOnQuestions(chatId, answer.getAnswer()));
                } else if (messageText.toLowerCase().contains("коллекции".toLowerCase()) || messageText.toLowerCase().contains("collections".toLowerCase())){
                    answerRepository.findById(Long.valueOf(6)).ifPresent(answer -> answersOnQuestions(chatId, answer.getTheme() + ":"));
                    answersOnQuestions(chatId, "Java Collection Framework — иерархия интерфейсов и их реализаций, которая является частью JDK" +
                            " и позволяет разработчику пользоваться большим количесвом структур данных из «коробки». Более подробно прочитай перейдя по ссылке ниже.");
                    answerRepository.findById(Long.valueOf(6)).ifPresent(answer -> answersOnQuestions(chatId, answer.getAnswer()));
                } else if (messageText.toLowerCase().contains("модификатор".toLowerCase())){
                    answerRepository.findById(Long.valueOf(8)).ifPresent(answer -> answersOnQuestions(chatId, answer.getTheme() + ":"));
                    answerRepository.findById(Long.valueOf(8)).ifPresent(answer -> answersOnQuestions(chatId, answer.getAnswer()));
                    answersOnQuestions(chatId, "Более подробно почитай тут:\n https://javarush.com/groups/posts/1988-modifikatorih-dostupa-private-protected-default-public");
                } else if (messageText.toLowerCase().contains("jvm".toLowerCase())){
                    answerRepository.findById(Long.valueOf(9)).ifPresent(answer -> answersOnQuestions(chatId, answer.getTheme() + ":"));
                    answerRepository.findById(Long.valueOf(9)).ifPresent(answer -> answersOnQuestions(chatId, answer.getAnswer()));
                } else if (messageText.toLowerCase().contains("jdk".toLowerCase())){
                    answerRepository.findById(Long.valueOf(12)).ifPresent(answer -> answersOnQuestions(chatId, answer.getTheme() + ":"));
                    answerRepository.findById(Long.valueOf(12)).ifPresent(answer -> answersOnQuestions(chatId, answer.getAnswer()));
                } else if (messageText.toLowerCase().contains("jre".toLowerCase())){
                    answerRepository.findById(Long.valueOf(11)).ifPresent(answer -> answersOnQuestions(chatId, answer.getTheme() + ":"));
                    answerRepository.findById(Long.valueOf(11)).ifPresent(answer -> answersOnQuestions(chatId, answer.getAnswer()));
                }else if (messageText.toLowerCase().contains("equals".toLowerCase()) || messageText.contains("hashCode".toLowerCase())){
                    answerRepository.findById(Long.valueOf(10)).ifPresent(answer -> answersOnQuestions(chatId, answer.getTheme() + ":"));
                    answerRepository.findById(Long.valueOf(10)).ifPresent(answer -> answersOnQuestions(chatId, answer.getAnswer()));
                    answersOnQuestions(chatId, "Это очень важная тема, на которой заваливают собеседование многие начинающие разработчики, поэтому рекомендую подробно почитать тут:\n https://tproger.ru/articles/equals-hashcode-java/");
                }
                else {
                    answersOnQuestions(chatId, "К сожалению меня этому еще не научили! Прошу прощения, я буду стараться!");
                }

            }
    }

    // TODO: метод приветствие после первого запуска бота новым пользователем
    private void startCommandReceived(long chatId, String name) {
        String answer = "Hi, " + name;
        System.out.println("Replied to user " + name);
        sendMessage(chatId, answer);
    }

    // TODO: метод в котором обрабатываются все наши вопросы
    private void answersOnQuestions(long chatId, String answer) {
        System.out.println("Replied to user " + answer);
        sendMessage(chatId, answer);
    }

    // TODO: метод из интерфейса телеграмм API, который отвечает за получения вопроса и вывод ответа пользователю
    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
