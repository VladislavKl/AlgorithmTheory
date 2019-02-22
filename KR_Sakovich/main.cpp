#include <iostream>
#include <vector>

using namespace std;

/*template <class Item>
class List{
public:
    List (long size=0);
    List(List&);
    ~List();
    List& operator=(List&);
    long Count() const;
    Item& Get(long index) const;
    Item& First() const;
    Item& Last() const;
    bool Includes(const Item&) const;

    void Append(const Item&);
    void Prepend(const Item&);

    void Remove (const Item&);
    void RemoveLast();
    void RemoveFirst();
    void RemoveAll();

    Item& Top() const;
    void Push(const Item&);
    Item& Pop();
};

template <class Item>
class Iterator{
public:
    virtual void First()=0;
    virtual void Next()=0;
    virtual bool IsDone() const = 0;
    virtual Item CurrentItem() const = 0;

protected:
    Iterator();
};

template <class Item>
class ListIterator:public Iterator<Item>{
    ListIterator(const List<Item>* aList);

    virtual void First();
    virtual void Next();
    virtual bool IsDone() const;
    virtual Item CurrentItem() const;

};*/


/*class Librarian {
public:
    virtual void update()=0;
};

class Library{
public:
    void addLibrarian(Librarian *observer)
    {
        _librarians.push_back(observer);
    }
    void notifyUpdate()
    {
        int size = _librarians.size();
        for (int i = 0; i < size; i++)
        {
            _librarians[i]->update();
        }
    }
private:
    vector<Librarian*> _librarians;
};



class LibraryModel : public Library
{
public:
    void addAmountOfBooks(int& a)
    {
        quantity.push_back(a);
    }
private:
    vector <int> quantity;
};


class ConsoleView: public Librarian
{
public:
    ConsoleView(LibraryModel *model)
    {
        _model = model;
        _model->addLibrarian(this);
    }
    virtual void update()
    {
        cout<<"Temperature in Celsius: "<< _model->getC()<<endl;
        cout<<"Temperature in Farenheit: "<< _model->getF()<<endl;
        cout<<"Input temperature in Celsius: ";
    }
private:
    LibraryModel *_model;
};



class Controller
{
public:
    Controller(LibraryModel *model)
    {
        _model = model;
    }
    void start()
    {
        _model->setC(0);

        float temp;
        do
        {
            scanf("%f", &temp);
            _model->setC(temp);
        }
        while (temp != 0);
    }
private:
    LibraryModel *_model;
};

int main()
{
    LibraryModel model;
    ConsoleView view(&model);
    Controller controller(&model);
    controller.start();
    return 0;
}
*/


class DynamicArray{
public:
    DynamicArray(){
        first=0;
        last=0;
        size=0;

    }

    DynamicArray(int const& q)
    {
        arr=new int[q];
        last=0;
        first=0;
        size=q;
    }

    ~DynamicArray(){}

    void push_back(int const& a)
    {
        if(last==0||last!=size-1)
        {
            arr[last]=a;
            ++last;
        }
        else
            arr[++last] = a;
    }
    void pop_front()
    {
        if (last!=0) {
            for (int i = 0; i < size - 1; ++i)
                arr[i] = arr[i + 1];
            --last;
        } else{
            throw exception();
        }
    }
    int getSize()
    {
        return size;
    }

private:
    int* arr;
    int first;
    int last;
    int size;

};


class Iterator{
public:
    virtual void First()=0;
    virtual void Next()=0;
    virtual bool IsDone() const = 0;
    virtual int CurrentItem() const = 0;

    protected:
        Iterator();
};

class IteratorArray: public Iterator
{
    void First();
    void Next();
    bool IsDone() const;
    int CurrentItem() const;

};



class Visitor{
public:

};


int main()
{
    return 0;

}