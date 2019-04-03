<?php


namespace list3\sorters\student;


use list3\sorters\ListSorter;
use list3\Student;

class FirstNameSorter implements ListSorter {

    public function sort(array $list): array {
        $newList = $list;
        usort($newList, function (Student $student1, Student $student2) {
            return strcmp($student1->getFirstName(), $student2->getFirstName());
        });
        return $newList;
    }
}