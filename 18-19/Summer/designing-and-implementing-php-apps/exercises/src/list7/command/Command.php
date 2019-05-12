<?php


namespace list7\command;


use DateTime;
use DateTimeImmutable;

abstract class Command {
    /**
     * @var DateTime
     */
    private $created;


    /**
     * Command constructor.
     */
    public function __construct() {
        $this->created = new DateTimeImmutable();
    }
}
