.data
MainANDi: .word 0
MainANDmainANDa: .float 0.0
MainANDmainANDj: .word 0
MainANDmainANDz: .word 0
MainANDmainANDres: .word 0

.text
.globl main
main:
la $t0, MainANDmainANDa
li.s $f0 ,0.2e-3
s.s $f0, ($t0)
la $t1, MainANDi
li $t2 ,3
sw $t2, ($t1)
la $t3, MainANDmainANDj
li $t4 ,21
sw $t4, ($t3)
la $t5, MainANDmainANDz
li $t6 ,22
sw $t6, ($t5)
la $t7, MainANDmainANDres
lw $t8 ,MainANDi
lw $t9 ,MainANDmainANDj
mulo $t9 , $t9 , $t8
lw $t2 ,MainANDmainANDz
add $t2 , $t2 , $t9
sw $t2, ($t7)

li $v0, 10
li $t0, 0
move $a0, $t0
syscall
print_string: 
print_int: 
print_float: 
read_int: 
read_string: 
read_float: 
exception: 
