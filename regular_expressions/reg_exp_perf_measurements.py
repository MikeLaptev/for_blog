'''
Created on Jan 18, 2017

@author: Mikhail

Working with command-line arguments:
https://pymotw.com/2/argparse/

Reading file's content:
https://docs.python.org/3/tutorial/inputoutput.html#methods-of-file-objects

Regular expressions in Python (compilation)
https://docs.python.org/3/library/re.html#regular-expression-objects

Execution time measurements
https://docs.python.org/3/library/timeit.html
'''

import argparse
from re import compile, findall
from timeit import timeit


def get_file_content(file):
    lines = list()
    with open(file) as f:
        for line in f:
            lines.append(line)
    return lines


def perform_match_with_compile_regexp(lines, regex_compiled):
    count = 0
    for line in lines:
        if len(regex_compiled.findall(line)) != 0:
            count += 1
    return count


def perform_match_with_string_regexp(lines, regex_str):
    count = 0
    for line in lines:
        if len(findall(regex_str, line)) != 0:
            count += 1
    return count

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-f', 
                        action='store', 
                        dest='file_name', 
                        help='Name of the file to regexp parser.', 
                        default='rfc793.txt')
    parser.add_argument('-r',
                        action='store',
                        dest='regex_str',
                        help='Regular expression to identify the match',
                        default='with (a|an|the|this) ')
    parser.add_argument('-i',
                        action='store',
                        dest='list_of_iterations',
                        help='List of iterations to execute performance measurements separated by space',
                        default='10 50 100 250 500 750 1000')
    parser.add_argument('-n',
                        action='store',
                        dest='number_of_experiments',
                        help='Number of experiments to calculate the average value',
                        default=10)
    arguments = parser.parse_args()
    lines = get_file_content(arguments.file_name)
    
    # preparing of regular expressions
    regex_str = arguments.regex_str
    regex_compiled = compile(regex_str)
    
    # preparing list of iterations
    list_of_iterations = list(map(int, arguments.list_of_iterations.split()))
    
    # assertion check
    print("Expected amount of expressions: {}.".format(
          perform_match_with_string_regexp(lines, regex_str)))
    assert perform_match_with_compile_regexp(lines, regex_compiled) == \
        perform_match_with_string_regexp(lines, regex_str)

    # performance check
    regular = [0 for _ in range(len(list_of_iterations))]
    compiled = [0 for _ in range(len(list_of_iterations))]
    
    # number of experiments
    number_of_experiments = arguments.number_of_experiments
    
    for j in range(number_of_experiments):
        i = 0
        print("Experiment {} started...".format(j+1))
        for n in list_of_iterations:
            compiled[i] += timeit("perform_match_with_compile_regexp(lines, regex_compiled)", 
                 number=n,
                 globals=globals())
            regular[i] += timeit("perform_match_with_string_regexp(lines, regex_str)", 
                 number=n,
                 globals=globals())
            i += 1
            
    averages_regular = [x/number_of_experiments for x in regular]
    averages_compiled = [x/number_of_experiments for x in compiled]
    
    print("Average result for compiled launches: ")
    for r in averages_compiled:
        print(r)
    print()
    
    print("Average results for regular launches: ")
    for r in averages_regular:
        print(r)
