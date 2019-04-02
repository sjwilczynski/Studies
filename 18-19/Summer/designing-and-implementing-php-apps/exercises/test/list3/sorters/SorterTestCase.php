<?php


namespace list3\sorters;


use DateTimeImmutable;
use list3\Application;
use list3\Student;
use PHPUnit\Framework\TestCase;

abstract class SorterTestCase extends TestCase {
    protected $students;
    protected $applications;

    protected function setUp(): void {
        $this->students = $this->initializeStudents();
        $this->applications = $this->initializeApplications($this->students);
    }


    public function initializeStudents(): array {
        $s1 = new Student("Joe", "Doe");
        $s2 = new Student("John", "Dow");
        $s3 = new Student("Doe", "Joe");
        $s4 = new Student("Johnny", "Donny");
        return array($s1, $s2, $s3, $s4);

    }

    public function initializeApplications(array $students): array {
        $a1 = new Application($students[0], new DateTimeImmutable("2019-03-16"), 30);
        $a2 = new Application($students[1], new DateTimeImmutable("2019-03-15"), 40);
        $a3 = new Application($students[2], new DateTimeImmutable("2019-03-17"), 50);
        $a4 = new Application($students[3], new DateTimeImmutable("2019-03-18"), 60);

        return array($a1, $a2, $a3, $a4);
    }
}