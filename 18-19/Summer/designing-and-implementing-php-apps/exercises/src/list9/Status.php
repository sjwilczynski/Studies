<?php


namespace list9;


use MyCLabs\Enum\Enum;


/**
 * @method static Status ACTIVE
 * @method static Status BLOCKED
 * @method static Status SUSPENDED
 * @method static Status CLOSED
 */
class Status extends Enum {

    private const ACTIVE = 'active';
    private const BLOCKED = 'blocked';
    private const SUSPENDED = 'suspended';
    private const CLOSED = 'closed';
}