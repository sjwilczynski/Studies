<?php

require __DIR__ . '/../vendor/autoload.php';

use list11\NumbersCommand;
use Symfony\Component\Console\Application;

$application = new Application();

$application->add(new NumbersCommand());

$application->run();