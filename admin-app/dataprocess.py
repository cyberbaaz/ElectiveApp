import csv

filename = './electivecsv1.csv'

fields = []
rows = []
rowsStudentDetails = []
fieldsStudentDetails = ['Roll','AvgCGPA','Dept','Email','Submitted']
rowsSubjectName = [
        ['BIOT4221',8,2022,'Open Elective','CSE',0,5,0],
        ['BIOT4222',8,2022,'Open Elective','CSE',0,100,0],
        ['BIOT4223',8,2022,'Open Elective','CSE',0,23,0],
        ['CHEN4222',8,2022,'Open Elective','CSE',0,11,0],
        ['AEIE4222',8,2022,'Open Elective','CSE',0,4,0],
        ['ECEN4223',8,2022,'Open Elective','CSE',0,8,0],
        ['ECEN4221',8,2022,'Open Elective','CSE',0,1,0],
        ['HMTS4222',8,2022,'Open Elective','CSE',0,46,0],
    ]
fieldsSubjectName = ['PCode','Semester','Year','ElectiveName','Dept','Count','MaxCount','Assigned']

fieldsPreferenceDetails = ['Roll','PCode','Semester','Year','Preference','Confirmed']
rowsPreferenceDetails = []

with open(filename, 'r') as csvfile:
    # creating a csv reader object
    csvreader = csv.reader(csvfile)
      
    # extracting field names through first row
    fields = next(csvreader)
    print(fields)
    # for field in 
    

    # extracting each data row one by one
    for row in csvreader:
        rows.append(row)
        rowsStudentDetails.append([row[2],row[4],'CSE',row[1],0])
        
        for i in range(5,13):
            preferenceDetails = [row[2],rowsSubjectName[i-5][0],8,2022,int(row[i]),0]
            preferenceDetails.extend([])
            rowsPreferenceDetails.append(preferenceDetails)
            # print(preferenceDetails)
  
    # get total number of rows
    print("Total no. of rows: %d"%(csvreader.line_num))

    print(rowsPreferenceDetails)
    


writefile1 = 'studentDetails.csv'
with open(writefile1, 'w',newline='') as csvfile: 
    # creating a csv writer object 
    csvwriter = csv.writer(csvfile) 
        
    # writing the fields 
    csvwriter.writerow(fieldsStudentDetails) 
        
    # writing the data rows 
    csvwriter.writerows(rowsStudentDetails)


writefile2 = 'subjectDetails.csv'
with open(writefile2, 'w', newline='') as csvfile: 
    # creating a csv writer object 
    csvwriter = csv.writer(csvfile) 
        
    # writing the fields 
    csvwriter.writerow(fieldsSubjectName) 
        
    # writing the data rows 
    csvwriter.writerows(rowsSubjectName)

writefile3 = 'preferenceDetails.csv'
with open(writefile3, 'w', newline='') as csvfile: 
    # creating a csv writer object 
    csvwriter = csv.writer(csvfile) 
        
    # writing the fields 
    csvwriter.writerow(fieldsPreferenceDetails) 
        
    # writing the data rows 
    csvwriter.writerows(rowsPreferenceDetails)



 