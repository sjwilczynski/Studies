<?php

namespace list8\event;

use DateTimeImmutable;
use Ramsey\Uuid\Uuid;
use Ramsey\Uuid\UuidInterface;

abstract class Event {
    /**
     * @var UuidInterface
     */
    private $id;

    /**
     * @var DateTimeImmutable
     */
    private $created;

    public function __construct() {
        $this->id = Uuid::uuid1();
        $this->created = new DateTimeImmutable();
    }
}