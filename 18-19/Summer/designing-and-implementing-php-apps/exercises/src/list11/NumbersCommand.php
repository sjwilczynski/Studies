<?php


namespace list11;

use list2\MyNumber;
use list2\RomanNumberFormatter;
use list2\RomanNumberRangeExceededException;
use Symfony\Component\Console\Command\Command;
use Symfony\Component\Console\Input\InputArgument;
use Symfony\Component\Console\Input\InputInterface;
use Symfony\Component\Console\Output\OutputInterface;


class NumbersCommand extends Command {

    protected static $defaultName = 'stachu:numbers';
    private static $NUMBER_ARGUMENT = 'number';
    private static $BASE_ARGUMENT = 'base';

    protected function configure() {
        $this->setDescription('Converts number to roman system');
        $this->setHelp('This command allows you to convert numbers to roman format. Just provide number you want to convert and its system ;)');

        $this->addArgument(self::$NUMBER_ARGUMENT, InputArgument::REQUIRED, 'number to convert');
        $this->addArgument(self::$BASE_ARGUMENT, InputArgument::OPTIONAL, 'base of the system in which number is provided (default is 10)', 10);
    }

    protected function execute(InputInterface $input, OutputInterface $output) {
        $output->writeln('Converting...');

        $number = $input->getArgument(self::$NUMBER_ARGUMENT);
        $base = $input->getArgument(self::$BASE_ARGUMENT);

        $value = new MyNumber(intval($number, $base), $base);
        //formatter powininen zostac wstrzykniety przez kontener DI
        $formatter = new RomanNumberFormatter();
        try {
            $formattedValue = $formatter->format($value);
            $output->writeln(['The result is:', $formattedValue]);
        } catch (RomanNumberRangeExceededException $e) {
            $output->writeln(['Number exceeded range allowed for roman numbers', 'It must fall within range [1,3999] and be an integer']);
        }

    }
}