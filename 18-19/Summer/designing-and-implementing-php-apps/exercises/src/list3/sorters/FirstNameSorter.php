<?php


namespace list3\sorters;


use list3\Application;

class FirstNameSorter implements ListSorter {

    public function sort(array $list): array {
        $newList = $list;
        usort($newList, function (Application $application1, Application $application2) {
            //Not ok with LSP?
            return strcmp($application1->getStudent()->getFirstName(), $application2->getStudent()->getFirstName());
        });
        return $newList;
    }
}