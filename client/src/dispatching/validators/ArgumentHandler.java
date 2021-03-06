package dispatching.validators;

import communication.Segment;
import dataSection.Commands;
import entities.JunkerCreator;
import exceptions.CommandSyntaxException;
import instructions.rotten.base.*;
import instructions.rotten.extended.*;
import instructions.rotten.RawDecree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Звено проверки аргументов команд.Реализация паттерна "Цепочка обязанностей" (Chain of Responsibility)
 * @author Come_1LL_F00 aka Lenar Khannanov
 * @author Leargy aka Anton Sushkevich
 */
public class ArgumentHandler extends DataHandler{
    private final HashMap<String,String> commandMap;

    /**
     * Конструктор принимающий список команд относительно которых будет производиться проверка.
     * @param commandList
     */
    public ArgumentHandler(Commands commandList){
        commandMap = commandList.getCommandMap();
        junkerCreator = new JunkerCreator();
    }

    /**
     * Метод седержащий логику проверки аргумента.
     * @param parcel
     * @return RawDecree
     * @throws CommandSyntaxException
     */
    @Override
    public RawDecree handle(Segment parcel) throws CommandSyntaxException {
        String tempCommand = parcel.getStringData()[0];

        boolean isLimited = false;
        Map.Entry<String,String> foundedCommand = commandMap.entrySet().stream().filter((a) -> (a.getValue().equals(tempCommand))).findFirst().get();
        if (foundedCommand.getKey().matches(".*\\s\\[(key|id)\\].*")) {
            isLimited = true;
        }

        String stringArgument = "";
        try {
            for (int i = 1; i < parcel.getStringData().length; i++) {
                stringArgument += parcel.getStringData()[i] ;
                if (i != parcel.getStringData().length - 1) {
                    stringArgument += " ";
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
             throw new CommandSyntaxException("Command should have at list one argument!");
        }
        Integer intArgument = null;
        if (isLimited) {
            try {
                intArgument = Integer.valueOf(stringArgument);
                if (intArgument < 0) throw new NumberFormatException();
                if (parcel.getStringData().length > 2) {
                    throw new CommandSyntaxException("Argument should be only one number!");
                }
            } catch (NumberFormatException e) {
                throw new CommandSyntaxException("Entered argument should be a positive integer!");
            }
            switch (foundedCommand.getValue()) {
                case RawRemoveKey.NAME: return new RawRemoveKey(intArgument);
                case RawInsert.NAME: return new RawInsert(intArgument, junkerCreator.prepareJunker());
                case RawUpdate.NAME: return new RawUpdate(intArgument, junkerCreator.prepareJunker());
                case RawReplaceIfLower.NAME: return new RawReplaceIfLower(intArgument, junkerCreator.prepareJunker());
                case RawReplaceIfGreater.NAME: return new RawReplaceIfGreater(intArgument, junkerCreator.prepareJunker());
            }
        }else {
            switch (foundedCommand.getValue()) {
                case RawExecuteScript.NAME: return new RawExecuteScript(stringArgument);
                case RawFilterContainsName.NAME: return new RawFilterContainsName(stringArgument);
                case RawRemoveLower.NAME: return new RawRemoveLower(junkerCreator.prepareJunker());
            }
        }
        return null;
    }
}
